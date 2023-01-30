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
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrudModel<S extends AbstractBean> {

    private final ObservableList<S> items = FXCollections.observableArrayList();

    private SimpleObjectProperty<S> selectedItem = new SimpleObjectProperty<>();

    public S getBeanInEdition() {
        return beanInEdition;
    }

    public void setBeanInEdition(S beanInEdition) {
        this.beanInEdition = beanInEdition;
    }

    private CrudGeneratorParameter parameter;

    private S beanInEdition;

    private S searchBean;

    public CrudModel(CrudGeneratorParameter parameter) {
        this.parameter = parameter;

        addListeners();
        refreshAction();
        setSearchBean(getNewBean());
    }

    private void addListeners() {

    }

    public void refreshAction(){
        items.setAll(parameter.getDataProvider().getAllItems(parameter.getClazz()));
    }

    public S getNewBean() {
        try {
            return (S) getParameter().getClazz().getDeclaredConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
            // let's not worry. It will not happen
            return null;
        }
    }
    public S addItemAction(){
        //if item selected then it should unselect current item, and create new empty item
        return getNewBean();
    }

    public void saveItemAction(){
        this.getSelectedItem().receiveChanges(this.getBeanInEdition());
        getSelectedItem().save();
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

    public CrudGeneratorParameter getParameter() {
        return parameter;
    }

    public S getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(S searchBean) {
        this.searchBean = searchBean;
    }
}
