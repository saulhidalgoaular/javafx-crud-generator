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
package com.saulpos.javafxcrudgenerator.model.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface AbstractDataProvider<S extends AbstractBean> {

    List<S> getAllItems(Class clazz) throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException;

    List<S> getAllItems(Class clazz, AbstractBean filter, SearchType type) throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException;

    /**
     * Here we will add all the classes that we want our
     * crud to list into the editor
     *
     * @param clazz
     * @return
     */
    boolean isRegisteredClass(Class clazz);

    void registerClass(Class clazz);

    List<Object[]> getItems(String query) throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException;

    enum SearchType {
        EQUAL, LIKE
    }
}
