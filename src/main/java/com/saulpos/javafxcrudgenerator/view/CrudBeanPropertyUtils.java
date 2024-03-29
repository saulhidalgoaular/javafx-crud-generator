/**
 * Copyright (c) 2013, ControlsFX
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import com.saulpos.javafxcrudgenerator.annotations.Search;
import com.saulpos.javafxcrudgenerator.model.Function;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputControl;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;

import java.beans.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Convenience utility class for creating {@link PropertySheet} instances based
 * on a JavaBean.
 */
public final class CrudBeanPropertyUtils {

    private CrudBeanPropertyUtils() {
        // no op
    }

    /**
     * Given a JavaBean, this method will return a list of {@link Item} intances,
     * which may be directly placed inside a {@link PropertySheet} (via its
     * {@link PropertySheet#getItems() items list}.
     * <p>
     * This method will not return read-only properties.
     *
     * @param bean              The JavaBean that should be introspected and be editable via
     *                          a {@link PropertySheet}.
     * @param translateFunction
     * @return A list of {@link Item} instances representing the properties of the
     * JavaBean.
     */
    public static ObservableList<Item> getProperties(final Object bean, boolean search, Function translateFunction) {
        ObservableList<Item> properties = getProperties(bean, (p) -> {
            if (p instanceof FeatureDescriptor) {
                try {
                    return !bean.getClass().getDeclaredField(p.getName()).isAnnotationPresent(Ignore.class) &&
                            (!search || bean.getClass().getDeclaredField(p.getName()).isAnnotationPresent(Search.class));
                } catch (NoSuchFieldException e) {
                    return false;
                }
            }
            return true;
        }, translateFunction);

        Collections.sort(properties, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return ((CrudPropertySheetItem) o1).getDisplayOrder() - ((CrudPropertySheetItem) o2).getDisplayOrder();
            }
        });
        return properties;
    }

    /**
     * Given a JavaBean, this method will return a list of {@link Item} intances,
     * which may be directly placed inside a {@link PropertySheet} (via its
     * {@link PropertySheet#getItems() items list}.
     *
     * @param bean The JavaBean that should be introspected and be editable via
     *      a {@link PropertySheet}.
     * @param test Predicate to test whether the property should be included in the 
     *      list of results.
     * @return A list of {@link Item} instances representing the properties of the
     *      JavaBean.
     */
    public static ObservableList<Item> getProperties(final Object bean, Predicate<PropertyDescriptor> test, Function translateFunction) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            for (PropertyDescriptor p : beanInfo.getPropertyDescriptors()) {
                if (test.test(p)) {
                    list.add(new CrudPropertySheetItem(bean, p, translateFunction));
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static void enableAutoSelectAll(TextInputControl control) {
        control.focusedProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(() -> {
                    control.selectAll();
                });
            }

        });
    }
}
