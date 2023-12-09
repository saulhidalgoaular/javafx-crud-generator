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

import com.saulpos.javafxcrudgenerator.annotations.Category;
import com.saulpos.javafxcrudgenerator.annotations.DisplayOrder;
import com.saulpos.javafxcrudgenerator.model.Function;
import org.controlsfx.property.BeanProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class CrudPropertySheetItem extends BeanProperty {


    private final Function translateFunction;

    public CrudPropertySheetItem(Object bean, PropertyDescriptor propertyDescriptor, Function translateFunction) {
        super(bean, propertyDescriptor);
        this.translateFunction = translateFunction;
    }

    @Override
    public String getName() {
        return ViewUtils.getName(super.getName(), translateFunction);
    }

    public String getOriginalName() {
        return super.getName();
    }

    @Override
    public String getCategory() {
        try {
            Field field = getBean().getClass().getDeclaredField(getOriginalName());
            if (field.isAnnotationPresent(Category.class)) {
                return field.getAnnotation(Category.class).name();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return Category.defaultCategory;
    }

    public int getDisplayOrder() {
        try {
            Field field = getBean().getClass().getDeclaredField(getOriginalName());
            if (field.isAnnotationPresent(DisplayOrder.class)) {
                return field.getAnnotation(DisplayOrder.class).orderValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }
}
