package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;

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

    public static HashMap<String, Property> createModelProperties(CrudGeneratorParameter parameter){
        final HashMap<String, Property> properties = new HashMap<>();
        for (Field field : parameter.getClazz().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Ignore.class)){
                Property property;

                if (SimpleStringProperty.class.equals(field.getType())) {
                    property = new SimpleStringProperty();
                }

                else if (SimpleBooleanProperty.class.equals(field.getType())) {
                    property = new SimpleBooleanProperty();
                }

                else if (SimpleObjectProperty.class.equals(field.getType()) && Calendar.class.equals(getActualTypeArgument(field))) {
                    property = new SimpleObjectProperty();
                }

                else {
                    property = new SimpleStringProperty();
                }

                properties.put(field.getName(), property);
            }
        }
        return properties;
    }

    public static String getTitle(final String name){
        StringBuilder title = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        for (char c : name.toCharArray()){
            if (currentWord.isEmpty()){
                currentWord.append(Character.toUpperCase(c));
            }else if (Character.isUpperCase(c)){
                title.append(currentWord);
                title.append(" ");
                currentWord.setLength(0);
                currentWord.append(Character.toUpperCase(c));
            }else{
                currentWord.append(c);
            }
        }
        if (!currentWord.isEmpty()){
            title.append(currentWord);
        }

        return title.toString().trim();
    }


}
