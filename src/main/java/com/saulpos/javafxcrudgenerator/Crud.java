package com.saulpos.javafxcrudgenerator;

import com.saulpos.javafxcrudgenerator.model.CrudModel;
import com.saulpos.javafxcrudgenerator.view.CrudView;

public class Crud<S> {

    private CrudModel<S> model;

    private CrudView view;

    public Crud() {
    }

    public Crud(CrudModel<S> model, CrudView view) {
        this.model = model;
        this.view = view;
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
