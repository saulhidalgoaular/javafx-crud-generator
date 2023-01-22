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
package com.saulpos.javafxcrudgenerator;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;

public class CrudGenerator {

    private CrudGeneratorParameter parameter;

    public CrudGenerator(CrudGeneratorParameter parameter) {
        this.parameter = parameter;
    }

    public Node generate(final Class clazz){
        final Field[] allFields = clazz.getDeclaredFields();

        // Fields area.
        final Pane fieldsPane = parameter.getFieldsLayout();

        final GridPane fieldsGridPane = new GridPane();
        fieldsGridPane.setPadding(new Insets(10, 10, 10, 30));
        fieldsGridPane.setHgap(10);
        fieldsGridPane.setVgap(10);

        int rowCounter = 0;
        for (Field field : allFields){
            final Node label = parameter.getLabelConstructor().generateNode(getTitle(field.getName()));
            final Control control = getControlField(field);

            fieldsGridPane.add(label, 0, rowCounter);
            fieldsGridPane.add(control, 1, rowCounter);

            rowCounter++;
        }
        fieldsPane.getChildren().add(fieldsGridPane);
        // Buttons area
        final Pane buttonsPane = parameter.getButtonLayout();
        final GridPane btnsGridPane = new GridPane();
        btnsGridPane.setPadding(new Insets(10, 10, 10, 30));
        btnsGridPane.setHgap(10);
        btnsGridPane.setVgap(10);

        final Node addNewButton =  parameter.getAddNextButtonConstructor().generateNode("Add New"); // TODO: Language customizable
        final Node editButton =  parameter.getEditButtonConstructor().generateNode("Edit");
        final Node deleteButton =  parameter.getDeleteButtonConstructor().generateNode("Delete");
        final Node refreshButton =  parameter.getRefreshButtonConstructor().generateNode("Refresh");

        btnsGridPane.add(addNewButton, 0, 0);
        btnsGridPane.add(editButton, 1, 0);
        btnsGridPane.add(deleteButton, 2, 0);
        btnsGridPane.add(refreshButton, 3, 0);

        buttonsPane.getChildren().addAll(btnsGridPane);
        //buttonsPane.getChildren().addAll(addNewButton, editButton, deleteButton, refreshButton);

        // Search area
        final GridPane searchGrigPane = new GridPane();
        searchGrigPane.setPadding(new Insets(10, 10, 10, 30));
        searchGrigPane.setHgap(10);
        searchGrigPane.setVgap(10);

        //final Pane searchArea = new HBox();
        final Label searchLabel = new Label("Search: ");
        final TextField searchBox = new TextField();
        final Label searchResult = new Label("Total");

        searchGrigPane.add(searchLabel, 0, 0);
        searchGrigPane.add(searchBox, 1, 0);
        searchGrigPane.add(searchResult, 2, 0);

        //searchGrigPane.getChildren().addAll(searchLabel, searchBox, searchResult);
        // Table area
        TableView tableView = new TableView();
        for (Field field : allFields){
            TableColumn<Object, String> column = new TableColumn<>(getTitle(field.getName())); // TODO: IT NEEDS SOME IMPROVEMENTS
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tableView.getColumns().add(column);
        }

        tableView.setPadding(new Insets(10, 10, 10, 10));


        final Pane mainPane = parameter.getMainLayout();

        final HBox mainSplit = new HBox();
        final VBox leftSide = new VBox();
        final VBox rightSide = new VBox();

        //mainPane.getChildren().addAll(fieldsPane, buttonsPane, searchGrigPane, tableView);
        leftSide.getChildren().addAll(fieldsPane, buttonsPane, searchGrigPane);
        rightSide.getChildren().add(tableView);
        mainSplit.getChildren().addAll(leftSide, rightSide);
        mainPane.getChildren().add(mainSplit);
        return mainPane;
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
