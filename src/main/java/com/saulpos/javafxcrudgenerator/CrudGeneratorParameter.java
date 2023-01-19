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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CrudGeneratorParameter {

    private String title = "";

    private Pane fieldsLayout = new VBox();

    private Pane buttonLayout = new HBox();

    private Pane mainLayout = new VBox();

    private boolean enableSearch = true;

    private NodeConstructor labelConstructor = new NodeConstructor() {

        @Override
        public Node generateNode(Object... name) {
            return new Label(name[0] + ":");
        }
    };

    private NodeConstructor buttonConstructor = new NodeConstructor() {
        @Override
        public Node generateNode(Object... name) {
            return new Button(name[0] + "");
        }
    };

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

    public NodeConstructor getLabelConstructor() {
        return labelConstructor;
    }

    public void setLabelConstructor(NodeConstructor labelConstructor) {
        this.labelConstructor = labelConstructor;
    }

    public Pane getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(Pane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public Pane getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(Pane buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

    public NodeConstructor getButtonConstructor() {
        return buttonConstructor;
    }

    public void setButtonConstructor(NodeConstructor buttonConstructor) {
        this.buttonConstructor = buttonConstructor;
    }
}
