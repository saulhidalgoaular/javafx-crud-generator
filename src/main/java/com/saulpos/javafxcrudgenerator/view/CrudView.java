package com.saulpos.javafxcrudgenerator.view;

import javafx.scene.Node;

public class CrudView {

    private Node mainView;

    public CrudView(Node mainView) {
        this.mainView = mainView;
    }

    public Node getMainView() {
        return mainView;
    }

    public void setMainView(Node mainView) {
        this.mainView = mainView;
    }
}
