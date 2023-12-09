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
package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.model.Function;
import javafx.scene.control.Button;

public class CustomButton {

    private NodeConstructor constructor;

    private Function function;

    // Useful in case we want to disable the button when nothing is selected.
    private boolean enableOnlyOnSelection;

    private Button button;

    public CustomButton(NodeConstructor constructor, Function function, boolean enableOnlyOnSelection) {
        this.constructor = constructor;
        this.function = function;
        this.enableOnlyOnSelection = enableOnlyOnSelection;
    }

    public CustomButton(NodeConstructor constructor, Function function) {
        this.constructor = constructor;
        this.function = function;
    }

    public Button getButton() {
        if (button == null) {
            button = (Button) getConstructor().generateNode(null);
        }
        return button;
    }

    public NodeConstructor getConstructor() {
        return constructor;
    }

    public void setConstructor(NodeConstructor constructor) {
        this.constructor = constructor;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public boolean isEnableOnlyOnSelection() {
        return enableOnlyOnSelection;
    }

    public void setEnableOnlyOnSelection(boolean enableOnlyOnSelection) {
        this.enableOnlyOnSelection = enableOnlyOnSelection;
    }
}
