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
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        setSearchBean(getNewBean());
        setBeanInEdition(getNewBean());
        addListeners();
        refreshAction();
    }

    private void addListeners() {
        final ChangeListener<Object> refreshChangeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue observableValue, Object s, Object newValue) {
                refreshAction();
            }
        };

        for (Method method :
                getSearchBean().getClass().getDeclaredMethods()) {
            try {
                if (!method.getName().endsWith("Property")){
                    continue;
                }
                final Object invoke = method.invoke(getSearchBean());
                final Method addListenerMethod = invoke.getClass().getMethod("addListener", ChangeListener.class);
                if (addListenerMethod != null){
                    addListenerMethod.invoke(invoke, refreshChangeListener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final CrudModel crudModel = this;
        crudModel.selectedItemProperty().addListener(new ChangeListener<S>() {
            @Override
            public void changed(ObservableValue<? extends S> observableValue, S s, S selectedRow) {
                try {
                    if (selectedRow == null){
                        crudModel.getBeanInEdition().receiveChanges(crudModel.getNewBean());
                    }else {
                        crudModel.getBeanInEdition().receiveChanges(selectedRow);
                    }
                }
                catch (Exception e) {
                    // this should not happen :/
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshAction(){
        items.setAll(parameter.getDataProvider().getAllItems(parameter.getClazz(), getSearchBean()));
    }

    private S getNewBean() {
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
        if (this.getSelectedItem() == null){
            final S newBean = this.getNewBean();
            newBean.receiveChanges(this.getBeanInEdition());
            newBean.save();
            this.getItems().add(newBean);
            //this.selectedItemProperty().setValue(newBean);
        }else{
            this.getSelectedItem().receiveChanges(this.getBeanInEdition());
            getSelectedItem().save();
        }
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
