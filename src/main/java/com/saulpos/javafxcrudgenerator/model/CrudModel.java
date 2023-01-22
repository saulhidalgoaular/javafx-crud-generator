package com.saulpos.javafxcrudgenerator.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrudModel<S> {

    private final ObservableList<S> items = FXCollections.observableArrayList();

    private S selectedItem;

    // TODO: Develop logic and bindings with view.
}
