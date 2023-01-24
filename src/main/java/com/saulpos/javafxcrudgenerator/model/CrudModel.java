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

package com.saulpos.javafxcrudgenerator.model;

import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CrudModel<S extends AbstractBean> {

    private final ObservableList<S> items = FXCollections.observableArrayList();

    private S selectedItem;

    private SimpleStringProperty searchText = new SimpleStringProperty();

    private HashMap<String, Property> properties = new HashMap<>();

    private CrudGeneratorParameter parameter;

    public CrudModel(CrudGeneratorParameter parameter) {
        this.parameter = parameter;

        createProperties();
    }

    public void createProperties(){
        properties.clear();
        for (Field field : parameter.getClazz().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Ignore.class)){
                properties.put(field.getName(), new SimpleStringProperty()); // TODO: Improve this
            }
        }
    }

    public HashMap<String, Property> getProperties() {
        return properties;
    }

    public ObservableList<S> getItems() {
        return items;
    }

    public S getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(S selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public SimpleStringProperty searchTextProperty() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.set(searchText);
    }


    // TODO: Develop logic and bindings with view.
}
