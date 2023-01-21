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

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
        for (Field field : allFields){
            final HBox row = new HBox();

            final Node label = parameter.getLabelConstructor().generateNode(field.getName());
            final Control control = getControlField(field);

            row.getChildren().addAll(label, control);

            fieldsPane.getChildren().add(row);
        }

        // Buttons area
        final Pane buttonsPane = parameter.getButtonLayout();
        final Node addNewButton =  parameter.getAddNextButtonConstructor().generateNode("Add New"); // TODO: Language customizable
        final Node editButton =  parameter.getEditButtonConstructor().generateNode("Edit");
        final Node deleteButton =  parameter.getDeleteButtonConstructor().generateNode("Delete");
        final Node refreshButton =  parameter.getRefreshButtonConstructor().generateNode("Refresh");

        buttonsPane.getChildren().addAll(addNewButton, editButton, deleteButton, refreshButton);

        final Pane searchArea = new HBox();
        final Label searchLabel = new Label("Search: ");
        final TextField searchBox = new TextField();

        final Label searchResult = new Label("Total");

        searchArea.getChildren().addAll(searchLabel, searchBox, searchResult);

        final Pane mainPane = parameter.getMainLayout();

        mainPane.getChildren().addAll(fieldsPane, buttonsPane, searchArea);



        return mainPane;
    }

    private Control getControlField(Field field){
        // TODO: Depending on the kind of field, we would need different controls.
        return new TextField();
    }


}
