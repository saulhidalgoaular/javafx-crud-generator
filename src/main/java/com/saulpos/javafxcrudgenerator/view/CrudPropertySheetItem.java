package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.annotations.Category;
import com.saulpos.javafxcrudgenerator.model.Function;
import org.controlsfx.property.BeanProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class CrudPropertySheetItem extends BeanProperty {


    private Function translateFunction;

    public CrudPropertySheetItem(Object bean, PropertyDescriptor propertyDescriptor, Function translateFunction) {
        super(bean, propertyDescriptor);
        this.translateFunction = translateFunction;
    }

    @Override
    public String getName() {
        return ViewUtils.getName(super.getName(), translateFunction);
    }

    public String getOriginalName(){
        return super.getName();
    }

    @Override
    public String getCategory() {
        try {
            Field field = getBean().getClass().getDeclaredField(getOriginalName());
            if (field.isAnnotationPresent(Category.class)){
                return field.getAnnotation(Category.class).name();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return Category.defaultCategory;
    }
}
