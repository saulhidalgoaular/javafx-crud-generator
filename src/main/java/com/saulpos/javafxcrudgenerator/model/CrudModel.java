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
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.lang.reflect.Method;

public class CrudModel<S extends AbstractBean> {

    private final ObservableList<S> items = FXCollections.observableArrayList();

    private SimpleObjectProperty<S> selectedItem = new SimpleObjectProperty<>();

    private SimpleStringProperty totalResult = new SimpleStringProperty();

    public S getBeanInEdition() {
        return beanInEdition;
    }

    public void setBeanInEdition(S beanInEdition) {
        this.beanInEdition = beanInEdition;
    }

    private CrudGeneratorParameter parameter;

    private S beanInEdition;

    private S searchBean;

    public CrudModel(CrudGeneratorParameter parameter) throws Exception {
        this.parameter = parameter;

        setSearchBean(getNewBean());
        setBeanInEdition(getNewBean());
        addListeners();
        refreshAction();
    }

    private void addListeners() {
        final ChangeListener<Object> refreshChangeListener = (observableValue, o, t1) -> {
            try {
                refreshAction();
            } catch (Exception e) {
                // TODO FIX ME.
                throw new RuntimeException(e);
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
        crudModel.selectedItemProperty().addListener((ChangeListener<S>) (observableValue, s, selectedRow) -> {
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
        });

        items.addListener(new ListChangeListener<S>() {
            @Override
            public void onChanged(Change<? extends S> change) {
                crudModel.setTotalResult( parameter.translate("total")+ ": " + items.size() + " "+ parameter.translate("items"));
            }
        });
    }

    public void refreshAction() throws Exception {
        items.setAll(parameter.getDataProvider().getAllItems(parameter.getClazz(), getSearchBean(), AbstractDataProvider.SearchType.LIKE));
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

    public void saveItemAction() throws Exception {
        Function beforeSave = this.getParameter().getBeforeSave();
        if (beforeSave != null){
            beforeSave.run(new Object[]{getBeanInEdition()});
        }
        if (this.getSelectedItem() == null){
            final S newBean = this.getNewBean();
            newBean.receiveChanges(getBeanInEdition());
            newBean.saveOrUpdate();
            this.getItems().add(newBean);
        }else{
            getSelectedItem().receiveChanges(getBeanInEdition());
            getSelectedItem().saveOrUpdate();
        }
    }

    public void deleteItemAction() throws Exception {
        Function beforeDelete = this.getParameter().getBeforeDelete();
        if (beforeDelete != null){
            beforeDelete.run(new Object[]{this.getBeanInEdition()});
        }
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

    public String getTotalResult() {
        return totalResult.get();
    }

    public SimpleStringProperty totalResultProperty() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult.set(totalResult);
    }

}
