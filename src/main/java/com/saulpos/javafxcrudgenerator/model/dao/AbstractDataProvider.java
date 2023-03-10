package com.saulpos.javafxcrudgenerator.model.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface AbstractDataProvider<S extends AbstractBean> {
    List<S> getAllItems(Class clazz) throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException;

    List<S> getAllItems(Class clazz, AbstractBean filter) throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException;

    /**
     * Here we will add all the classes that we want our
     * crud to list into the editor
     * @param clazz
     * @return
     */
    boolean isRegisteredClass(Class clazz);

    void registerClass(Class clazz);

    // TODO: We should implement later some filtering directly over database.
}
