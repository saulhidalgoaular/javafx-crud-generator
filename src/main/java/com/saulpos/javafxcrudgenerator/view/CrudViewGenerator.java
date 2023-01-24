/*
 * Copyright (C) 2012-2023 Saúl Hidalgo <saulhidalgoaular at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.NodeConstructor;
import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.annotations.Password;
import com.saulpos.javafxcrudgenerator.annotations.RichCalendar;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.saulpos.javafxcrudgenerator.view.ViewUtils;
import jfxtras.scene.control.CalendarPicker;

import java.lang.reflect.*;
import java.util.*;

public class CrudViewGenerator {

    private CrudGeneratorParameter parameter;

    private Node addNewButton;
    private Node editButton;
    private Node deleteButton;
    private Node refreshButton;
    private Label searchResult;
    private TableView tableView;
    private TextField searchBox;

    private HashMap<String, Node> parameterNodes = new HashMap<>();

    public CrudViewGenerator(CrudGeneratorParameter parameter) {
        this.parameter = parameter;
    }

    public CrudView generate(){
        final Field[] allFields = parameter.getClazz().getDeclaredFields();

        // Fields area.
        final Pane fieldsPane = createFieldsPane(allFields);
        // Buttons area
        final Pane buttonsPane = createButtonsPane();
        // Search area
        final GridPane searchGridPane = createSearchPane();
        // Table area
        tableView = createTableViewPane(allFields);

        Pane mainPane = createMainPane(fieldsPane, buttonsPane, searchGridPane, tableView);

        addBindings();

        CrudView view = new CrudView();
        view.setMainView(mainPane);
        view.setTableView(tableView);
        view.setAddNewButton(addNewButton);
        view.setEditButton(editButton);
        view.setDeleteButton(deleteButton);
        view.setRefreshButton(refreshButton);
        view.setParameterNodes(parameterNodes);
        view.setSearchBox(searchBox);
        view.setTotalLabel(searchResult);

        return view;
    }

    private void addBindings() {
        deleteButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        editButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    private Pane createMainPane(Pane fieldsPane, Pane buttonsPane, GridPane searchGridPane, TableView tableView) {
        final Pane mainPane = parameter.getMainLayout();

        final HBox mainSplit = new HBox();
        final VBox leftSide = new VBox();
        final VBox rightSide = new VBox();
        searchResult = new Label("Total");
        leftSide.getChildren().addAll(searchGridPane, tableView, searchResult);
        rightSide.getChildren().addAll(fieldsPane, buttonsPane);
        mainSplit.getChildren().addAll(leftSide, rightSide);
        mainPane.getChildren().add(mainSplit);
        return mainPane;
    }

    private TableView createTableViewPane(Field[] allFields) {
        TableView tableView = new TableView();
        for (Field field : allFields){
            if (!field.isAnnotationPresent(Ignore.class)){
                TableColumn<Object, String> column = new TableColumn<>(getTitle(field.getName())); // TODO: IT NEEDS SOME IMPROVEMENTS
                column.setCellValueFactory(cell -> getProperty(cell, field.getName()));
                tableView.getColumns().add(column);
            }
        }

        tableView.setPadding(new Insets(10, 10, 10, 10));
        return tableView;
    }

    private Property getProperty(final TableColumn.CellDataFeatures instance, final String name){
        try {
            return (Property) parameter.getClazz().getDeclaredMethod(name + "Property").invoke(instance.getValue());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private GridPane createSearchPane() {
        final GridPane searchGridPane = new GridPane();
        searchGridPane.setPadding(new Insets(10, 10, 10, 10));
        searchGridPane.setHgap(10);
        searchGridPane.setVgap(10);

        final Label searchLabel = new Label("Search: ");
        searchBox = new TextField();

        searchGridPane.add(searchLabel, 0, 0);
        searchGridPane.add(searchBox, 1, 0);
        return searchGridPane;
    }

    private Pane createButtonsPane() {
        final Pane buttonsPane = parameter.getButtonLayout();
        final GridPane btnsGridPane = new GridPane();
        btnsGridPane.setPadding(new Insets(10, 10, 10, 30));
        btnsGridPane.setHgap(10);
        btnsGridPane.setVgap(10);

        addNewButton =  parameter.getAddNextButtonConstructor().generateNode("Add New"); // TODO: Language customizable
        editButton =  parameter.getEditButtonConstructor().generateNode("Edit");
        deleteButton =  parameter.getDeleteButtonConstructor().generateNode("Delete");
        refreshButton =  parameter.getRefreshButtonConstructor().generateNode("Refresh");

        final Node[] nodes = new Node[]{addNewButton, editButton, deleteButton, refreshButton};
        final ArrayList<Node> allButtons = new ArrayList<>(Arrays.asList(nodes));
        for (Object customButtonConstructor :
                parameter.getExtraButtonsConstructor()) {
            allButtons.add(((NodeConstructor)customButtonConstructor).generateNode(null));
        }

        for (int i = 0; i < allButtons.size(); i++) {
            btnsGridPane.add(allButtons.get(parameter.getButtonsOrder().isEmpty() ? i :(int)parameter.getButtonsOrder().get(i)), i, 0);
        }

        buttonsPane.getChildren().addAll(btnsGridPane);
        return buttonsPane;
    }

    private Pane createFieldsPane(Field[] allFields) {
        final Pane fieldsPane = parameter.getFieldsLayout();

        final GridPane fieldsGridPane = new GridPane();
        fieldsGridPane.setPadding(new Insets(10, 10, 10, 30));
        fieldsGridPane.setHgap(10);
        fieldsGridPane.setVgap(10);

        int rowCounter = 0;
        for (Field field : allFields){
            if (!field.isAnnotationPresent(Ignore.class)) {
                final Node label = parameter.getLabelConstructor().generateNode(getTitle(field.getName()));
                final Control control = getControlField(field);

                parameterNodes.put(field.getName(), control);

                fieldsGridPane.add(label, 0, rowCounter);
                fieldsGridPane.add(control, 1, rowCounter);

                rowCounter++;
            }
        }
        fieldsPane.getChildren().add(fieldsGridPane);
        return fieldsPane;
    }

    private Control getControlField(Field field){
        // TODO: Depending on the kind of field, we would need different controls.

        if (SimpleStringProperty.class.equals(field.getType())) {
            if (field.isAnnotationPresent(Password.class)) {
                return new PasswordField();
            }
            if (field.isAnnotationPresent(LongString.class)) {
                return new TextArea();
            }
            return new TextField();
        }

        else if (SimpleBooleanProperty.class.equals(field.getType())) {
            return new CheckBox();
        }

        else if (SimpleObjectProperty.class.equals(field.getType()) && Calendar.class.equals(ViewUtils.getActualTypeArgument(field))) {
            if (field.isAnnotationPresent(RichCalendar.class)) {
                return new CalendarPicker();
            }
            else
                return new DatePicker();

        }

        else {
            return new TextField();
        }
    }



    private static String getTitle(final String name){
        StringBuilder title = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        for (char c : name.toCharArray()){
            if (currentWord.isEmpty()){
                currentWord.append(Character.toUpperCase(c));
            }else if (Character.isUpperCase(c)){
                title.append(currentWord);
                title.append(" ");
                currentWord.setLength(0);
                currentWord.append(Character.toUpperCase(c));
            }else{
                currentWord.append(c);
            }
        }
        if (!currentWord.isEmpty()){
            title.append(currentWord);
        }

        return title.toString().trim();
    }

    public Node getAddNewButton() {
        return addNewButton;
    }

    public Node getEditButton() {
        return editButton;
    }

    public Node getDeleteButton() {
        return deleteButton;
    }

    public Node getRefreshButton() {
        return refreshButton;
    }
}
