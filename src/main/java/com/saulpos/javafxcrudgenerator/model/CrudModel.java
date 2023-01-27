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

    public S getBeanInEdition() {
        return beanInEdition;
    }

    public void setBeanInEdition(S beanInEdition) {
        this.beanInEdition = beanInEdition;
    }

    private CrudGeneratorParameter parameter;

    private S beanInEdition;

    public CrudModel(CrudGeneratorParameter parameter) {
        this.parameter = parameter;

        addListeners();
        refreshAction();
    }

    private void addListeners() {

    }

    public void refreshAction(){
        items.setAll(parameter.getDataProvider().getAllItems(parameter.getClazz()));
    }

    public void addItemAction(){
        // TODO: Call default constructor and assign the values
    }

    public void editItemAction(){
        this.getSelectedItem().receiveChanges(this.getBeanInEdition());
    }

    public void deleteItemAction(){
        selectedItem.get().delete();
        items.remove(selectedItem.get());
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

    public CrudGeneratorParameter getParameter() {
        return parameter;
    }
}
