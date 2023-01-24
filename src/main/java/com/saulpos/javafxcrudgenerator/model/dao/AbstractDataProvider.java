package com.saulpos.javafxcrudgenerator.model.dao;

import java.util.List;

public interface AbstractDataProvider<S extends AbstractBean> {
    List<S> getAllItems(Class clazz);

    // TODO: We should implement later some filtering directly over database.
}
