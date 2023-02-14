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

import com.saulpos.javafxcrudgenerator.model.Function;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import com.saulpos.javafxcrudgenerator.view.NodeConstructor;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CrudGeneratorParameter <S extends AbstractBean> {

    private String title = "";

    private Pane fieldsLayout = new VBox();

    private Pane buttonLayout = new FlowPane();

    private Integer buttonWidth = 120;

    private SplitPane mainLayout = new SplitPane();

    private boolean enableSearch = true;

    private boolean hidePropertyEditor = false;

    private Class clazz; // TODO: Improve the logic of it.

    public Integer getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(Integer buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    private NodeConstructor genericButtonConstructor = new NodeConstructor() {
        /**
         * 1st param is the name and the 2nd is the icon
         * @param parameters
         * @return
         */
        @Override
        public Node generateNode(Object... parameters) {
            Button button = new Button();
            Label icon = GlyphsDude.createIconLabel((GlyphIcons) parameters[1], parameters[0] + "", "20px", "10px", ContentDisplay.LEFT);
            button.setGraphic(icon);
            button.setMinWidth(getButtonWidth());
            return button;
        }
    };

    private NodeConstructor addNextButtonConstructor = genericButtonConstructor;
    private NodeConstructor editButtonConstructor = genericButtonConstructor;
    private NodeConstructor deleteButtonConstructor = genericButtonConstructor;
    private NodeConstructor refreshButtonConstructor = genericButtonConstructor;

    private ArrayList<Integer> buttonsOrder = new ArrayList<>();

    private ArrayList<NodeConstructor> extraButtonsConstructor = new ArrayList<>();

    private AbstractDataProvider<S> dataProvider;

    private Function beforeSave;

    private Function beforeDelete;

    public Function getBeforeSave() {
        return beforeSave;
    }

    public void setBeforeSave(Function beforeSave) {
        this.beforeSave = beforeSave;
    }

    public Function getBeforeDelete() {
        return beforeDelete;
    }

    public void setBeforeDelete(Function beforeDelete) {
        this.beforeDelete = beforeDelete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pane getFieldsLayout() {
        return fieldsLayout;
    }

    public void setFieldsLayout(Pane fieldsLayout) {
        this.fieldsLayout = fieldsLayout;
    }

    public boolean isEnableSearch() {
        return enableSearch;
    }

    public void setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    public SplitPane getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(SplitPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public Pane getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(Pane buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

    public NodeConstructor getAddNextButtonConstructor() {
        return addNextButtonConstructor;
    }

    public void setAddNextButtonConstructor(NodeConstructor addNextButtonConstructor) {
        this.addNextButtonConstructor = addNextButtonConstructor;
    }

    public NodeConstructor getGenericButtonConstructor() {
        return genericButtonConstructor;
    }

    public void setGenericButtonConstructor(NodeConstructor genericButtonConstructor) {
        this.genericButtonConstructor = genericButtonConstructor;
    }

    public NodeConstructor getEditButtonConstructor() {
        return editButtonConstructor;
    }

    public void setEditButtonConstructor(NodeConstructor editButtonConstructor) {
        this.editButtonConstructor = editButtonConstructor;
    }

    public NodeConstructor getDeleteButtonConstructor() {
        return deleteButtonConstructor;
    }

    public void setDeleteButtonConstructor(NodeConstructor deleteButtonConstructor) {
        this.deleteButtonConstructor = deleteButtonConstructor;
    }

    public NodeConstructor getRefreshButtonConstructor() {
        return refreshButtonConstructor;
    }

    public void setRefreshButtonConstructor(NodeConstructor refreshButtonConstructor) {
        this.refreshButtonConstructor = refreshButtonConstructor;
    }

    public ArrayList<NodeConstructor> getExtraButtonsConstructor() {
        return extraButtonsConstructor;
    }

    public void setExtraButtonsConstructor(ArrayList<NodeConstructor> extraButtonsConstructor) {
        this.extraButtonsConstructor = extraButtonsConstructor;
    }

    public ArrayList<Integer> getButtonsOrder() {
        return buttonsOrder;
    }

    public void setButtonsOrder(ArrayList<Integer> buttonsOrder) {
        this.buttonsOrder = buttonsOrder;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public AbstractDataProvider<S> getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(AbstractDataProvider<S> dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void setHidePropertyEditor(boolean hidePropertyEditor) {
        this.hidePropertyEditor = hidePropertyEditor;
    }

    public boolean isHidePropertyEditor() {
        return hidePropertyEditor;
    }
}
