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
import com.saulpos.javafxcrudgenerator.annotations.*;
import javafx.beans.property.*;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;

import java.lang.reflect.*;
import java.util.*;

public class CrudViewGenerator {

    private CrudGeneratorParameter parameter;

    private PropertySheet propertySheet;
    private Node addNewButton;
    private Node editButton;
    private Node deleteButton;
    private Node refreshButton;
    private Label searchResult;
    private TableView tableView;
    private TextField searchBox;

    public CrudViewGenerator(CrudGeneratorParameter parameter) {
        this.parameter = parameter;
    }

    public CrudView generate(){
        final Field[] allFields = parameter.getClazz().getDeclaredFields();

        // Fields area.
        final Pane fieldsPane = createFieldsPane();
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
        view.setSaveButton(editButton);
        view.setDeleteButton(deleteButton);
        view.setRefreshButton(refreshButton);
        view.setPropertySheet(propertySheet);
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
                TableColumn<Object, String> column = new TableColumn<>(ViewUtils.getTitle(field.getName()));
                column.setCellValueFactory(cell -> getProperty(cell, field.getName()));
                column.setCellFactory(tableColum -> getCellFactory(field));
                tableView.getColumns().add(column);
            }
        }

        tableView.setPadding(new Insets(10, 10, 10, 10));
        return tableView;
    }

    private static TableCell<Object, String> getCellFactory(final Field field) {
        if (SimpleBooleanProperty.class.equals(field.getType())){
            return new CheckBoxTableCell<>();
        }
        return new TextFieldTableCell<>();
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
        editButton =  parameter.getEditButtonConstructor().generateNode("Save");
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

    private Pane createFieldsPane() {
        final Pane fieldsPane = parameter.getFieldsLayout();

        propertySheet = new PropertySheet();

        fieldsPane.getChildren().add(propertySheet);
        return fieldsPane;
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
