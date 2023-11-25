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
package com.saulpos.javafxcrudgenerator.presenter;

import com.saulpos.javafxcrudgenerator.model.CrudModel;
import com.saulpos.javafxcrudgenerator.model.Function;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.view.*;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;

import java.util.ArrayList;

public class CrudPresenter<S extends AbstractBean> {

    private CrudModel<S> model;

    private CrudView view;

    public CrudPresenter() {
    }

    public CrudPresenter(CrudModel<S> model, CrudView view) throws Exception {
        this.model = model;
        this.view = view;

        addBindings(); // Optional
        addActions();
        addInitialBean();


    }

    S createInstance(Class<S> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInitialBean() throws Exception {
        newInitialBean();
    }

    public void addActions(){
        ((Button)view.getAddNewButton()).setOnAction(actionEvent -> newInitialBean());

        ((Button)view.getSaveButton()).setOnAction(actionEvent -> {
            try {
                model.saveItemAction();
            } catch (Exception e) {
                DialogBuilder.createExceptionDialog("Exception saving the item", "SAUL POS", e.getMessage(), e).showAndWait();
            }
        });

        ((Button)view.getDeleteButton()).setOnAction(actionEvent -> {
            try {
                model.deleteItemAction();
            } catch (Exception e) {
                DialogBuilder.createExceptionDialog("Exception deleting the item", "SAUL POS", e.getMessage(), e).showAndWait();
            }
        });

        ((Button)view.getRefreshButton()).setOnAction(actionEvent -> {
            try {
                model.refreshAction();
            } catch (Exception e) {
                DialogBuilder.createExceptionDialog("Exception Refreshing", "SAUL POS", e.getMessage(), e).showAndWait();
            }
        });

        for (int i = 0; i < model.getParameter().getExtraButtonsConstructor().size(); i++) {
            final int finalIntI = i;
            ((Button)view.getExtraButtons().get(i)).setOnAction(event -> {
                ArrayList<Function> extraButtonsFunction = model.getParameter().getExtraButtonsFunction();
                try {
                    extraButtonsFunction.get(finalIntI).run(new Object[]{model.getBeanInEdition()});
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void newInitialBean() {
        view.getTableView().getSelectionModel().select(null);
    }

    public void addBindings(){
        Bindings.bindContentBidirectional(view.getTableView().getItems(), model.getItems());
        model.selectedItemProperty().bind(view.getTableView().getSelectionModel().selectedItemProperty());

        if(!model.getParameter().isHidePropertyEditor()) {
            view.getPropertySheet().setPropertyEditorFactory(
                    CrudPropertyEditorFactory.getCrudPropertyEditorFactory(model.getParameter().getDataProvider()));
            view.getPropertySheet().getItems().setAll(CrudBeanPropertyUtils.getProperties(model.getBeanInEdition(), false, model.getParameter().getTranslateFunction()));
        }
        if (view.getSearchPropertySheet() != null){
            view.getSearchPropertySheet().getItems().setAll(CrudBeanPropertyUtils.getProperties(model.getSearchBean(), true, model.getParameter().getTranslateFunction()));
            view.getSearchPropertySheet().setPropertyEditorFactory(
                    CrudPropertyEditorFactory.getCrudPropertyEditorFactory(model.getParameter().getDataProvider())
            );
        }

        view.getTotalLabel().textProperty().bind(model.totalResultProperty());
    }

    public CrudModel<S> getModel() {
        return model;
    }

    public void setModel(CrudModel<S> model) {
        this.model = model;
    }

    public CrudView getView() {
        return view;
    }

    public void setView(CrudView view) {
        this.view = view;
    }
}
