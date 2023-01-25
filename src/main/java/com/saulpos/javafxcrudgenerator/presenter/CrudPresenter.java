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
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.view.CrudView;
import com.saulpos.javafxcrudgenerator.view.ViewUtils;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.property.BeanPropertyUtils;

public class CrudPresenter<S extends AbstractBean> {

    private CrudModel<S> model;

    private CrudView view;

    public CrudPresenter() {
    }

    public CrudPresenter(CrudModel<S> model, CrudView view) {
        this.model = model;
        this.view = view;

        addBindings(); // Optional
        addActions();
    }

    public void addActions(){
        ((Button)view.getAddNewButton()).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.addItemAction();
            }
        }); // TODO: Improve this. It is not Buttons all the time

        ((Button)view.getEditButton()).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.editItemAction();
            }
        });

        ((Button)view.getDeleteButton()).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.deleteItemAction();
            }
        });

        ((Button)view.getRefreshButton()).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.refreshAction();
            }
        });
    }

    public void addBindings(){
        Bindings.bindContentBidirectional(view.getTableView().getItems(), model.getItems());
        model.selectedItemProperty().bind(view.getTableView().getSelectionModel().selectedItemProperty());

        model.selectedItemProperty().addListener(new ChangeListener<S>() {
            @Override
            public void changed(ObservableValue<? extends S> observableValue, S s, S t1) {
                view.getPropertySheet().getItems().setAll(BeanPropertyUtils.getProperties(t1));
            }
        });

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
