package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.model.Function;
import javafx.scene.Node;
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
        if (button == null){
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
