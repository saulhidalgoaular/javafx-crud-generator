package com.saulpos.javafxcrudgenerator.view;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ViewUtils {
    public static Type getActualTypeArgument(Field field) {
        if (field.getGenericType() instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            if (actualTypeArguments.length > 0){
                return actualTypeArguments[0];
            }
        }
        return null;
    }
}
