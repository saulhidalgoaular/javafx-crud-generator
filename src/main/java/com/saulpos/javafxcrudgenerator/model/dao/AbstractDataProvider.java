package com.saulpos.javafxcrudgenerator.model.dao;

import java.util.List;

public interface AbstractDataProvider<S extends AbstractBean> {
    List<S> getAllItems(Class clazz);

    List<S> getAllItems(Class clazz, AbstractBean filter);

    /**
     * Here we will add all the classes that we want our
     * crud to list into the editor
     * @param clazz
     * @return
     */
    boolean isRegisteredClass(Class clazz);

    // TODO: We should implement later some filtering directly over database.
}
