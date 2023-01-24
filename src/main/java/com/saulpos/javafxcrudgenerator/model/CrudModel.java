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
import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.annotations.Password;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.view.ViewUtils;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class CrudModel<S extends AbstractBean> {

    private final ObservableList<S> items = FXCollections.observableArrayList();

    private SimpleObjectProperty<S> selectedItem = new SimpleObjectProperty<>();

    private SimpleStringProperty searchText = new SimpleStringProperty();

    private final HashMap<String, Property> properties;

    private CrudGeneratorParameter parameter;

    public CrudModel(CrudGeneratorParameter parameter) {
        this.parameter = parameter;

        addListeners();
        refreshAction();
        properties = ViewUtils.createModelProperties(parameter);
    }

    private void addListeners() {
        selectedItem.addListener(new ChangeListener<S>() {
            @Override
            public void changed(ObservableValue<? extends S> observableValue, S oldValue, S newValue) {
                for (String field : properties.keySet()){
                    try {
                        properties.get(field).setValue(newValue != null ? parameter.getClazz().getDeclaredMethod("get" + Character.toUpperCase(field.charAt(0)) + field.substring(1) ).invoke(newValue) : null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void refreshAction(){
        items.setAll(parameter.getDataProvider().getAllItems(parameter.getClazz()));
    }

    public void addItemAction(){
        // TODO: Call default constructor and assign the values
    }

    public void editItemAction(){
        final HashMap<String, Method> methods = new HashMap<>();
        Arrays.stream(parameter.getClazz().getDeclaredMethods()).forEach(method -> methods.put(method.getName(), method));

        for (String field : properties.keySet()){
            try {
                methods.get("set" + Character.toUpperCase(field.charAt(0)) + field.substring(1))
                    .invoke(
                    selectedItem.get(),
                    properties.get(field).getValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteItemAction(){
        selectedItem.get().delete();
        items.remove(selectedItem.get());
    }

    public HashMap<String, Property> getProperties() {
        return properties;
    }

    public ObservableList<S> getItems() {
        return items;
    }

    public AbstractBean getSelectedItem() {
        return selectedItem.get();
    }

    public SimpleObjectProperty<S> selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(S selectedItem) {
        this.selectedItem.set(selectedItem);
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
