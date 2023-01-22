package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.NodeConstructor;
import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class CrudViewGenerator {

    private CrudGeneratorParameter parameter;

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
        TableView tableView = createTableViewPane(allFields);

        return new CrudView(createMainPane(fieldsPane, buttonsPane, searchGridPane, tableView));
    }

    private Pane createMainPane(Pane fieldsPane, Pane buttonsPane, GridPane searchGridPane, TableView tableView) {
        final Pane mainPane = parameter.getMainLayout();

        final HBox mainSplit = new HBox();
        final VBox leftSide = new VBox();
        final VBox rightSide = new VBox();

        leftSide.getChildren().addAll(fieldsPane, buttonsPane);
        rightSide.getChildren().addAll(searchGridPane, tableView);
        mainSplit.getChildren().addAll(leftSide, rightSide);
        mainPane.getChildren().add(mainSplit);
        return mainPane;
    }

    private static TableView createTableViewPane(Field[] allFields) {
        TableView tableView = new TableView();
        for (Field field : allFields){
            if (!field.isAnnotationPresent(Ignore.class)){
                TableColumn<Object, String> column = new TableColumn<>(getTitle(field.getName())); // TODO: IT NEEDS SOME IMPROVEMENTS
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }
        }

        tableView.setPadding(new Insets(10, 10, 10, 10));
        return tableView;
    }

    private static GridPane createSearchPane() {
        final GridPane searchGridPane = new GridPane();
        searchGridPane.setPadding(new Insets(10, 10, 10, 30));
        searchGridPane.setHgap(10);
        searchGridPane.setVgap(10);

        final Label searchLabel = new Label("Search: ");
        final TextField searchBox = new TextField();
        final Label searchResult = new Label("Total");

        searchGridPane.add(searchLabel, 0, 0);
        searchGridPane.add(searchBox, 1, 0);
        searchGridPane.add(searchResult, 2, 0);
        return searchGridPane;
    }

    private Pane createButtonsPane() {
        final Pane buttonsPane = parameter.getButtonLayout();
        final GridPane btnsGridPane = new GridPane();
        btnsGridPane.setPadding(new Insets(10, 10, 10, 30));
        btnsGridPane.setHgap(10);
        btnsGridPane.setVgap(10);

        final Node addNewButton =  parameter.getAddNextButtonConstructor().generateNode("Add New"); // TODO: Language customizable
        final Node editButton =  parameter.getEditButtonConstructor().generateNode("Edit");
        final Node deleteButton =  parameter.getDeleteButtonConstructor().generateNode("Delete");
        final Node refreshButton =  parameter.getRefreshButtonConstructor().generateNode("Refresh");

        final Node[] nodes = new Node[]{addNewButton, editButton, deleteButton, refreshButton};
        final ArrayList<Node> allButtons = new ArrayList<>(Arrays.asList(nodes));
        for (NodeConstructor customButtonConstructor :
                parameter.getExtraButtonsConstructor()) {
            allButtons.add(customButtonConstructor.generateNode(null));
        }

        for (int i = 0; i < allButtons.size(); i++) {
            btnsGridPane.add(allButtons.get(parameter.getButtonsOrder().isEmpty() ? i :parameter.getButtonsOrder().get(i)), i, 0);
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
        return new TextField();
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
}
