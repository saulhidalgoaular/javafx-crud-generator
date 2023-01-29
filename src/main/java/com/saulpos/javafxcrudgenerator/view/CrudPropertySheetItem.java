package com.saulpos.javafxcrudgenerator.view;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanProperty;

import java.beans.PropertyDescriptor;

public class CrudPropertySheetItem extends BeanProperty {

    public CrudPropertySheetItem(Object bean, PropertyDescriptor propertyDescriptor) {
        super(bean, propertyDescriptor);
    }

    @Override
    public String getName() {
        return ViewUtils.getTitle(super.getName());
    }

    public String getOriginalName(){
        return super.getName();
    }
}
