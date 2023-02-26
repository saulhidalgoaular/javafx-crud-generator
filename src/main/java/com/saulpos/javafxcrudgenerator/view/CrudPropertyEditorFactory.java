package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.annotations.Password;
import com.saulpos.javafxcrudgenerator.annotations.Readonly;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URISyntaxException;

public class CrudPropertyEditorFactory {


    public static Callback<PropertySheet.Item, PropertyEditor<?>> getCrudPropertyEditorFactory(AbstractDataProvider dataProvider) {
        return new Callback<PropertySheet.Item, PropertyEditor<?>>() {
            @Override
            public PropertyEditor<?> call(PropertySheet.Item item) {
                if (dataProvider.isRegisteredClass(item.getType())) {
                    try {
                        return Editors.createChoiceEditor(item, dataProvider.getAllItems(item.getType()));
                    } catch (Exception e) {
                        // TODO FIX ME
                        throw new RuntimeException(e);
                    }
                }

                if (item instanceof CrudPropertySheetItem) {
                    CrudPropertySheetItem itemCrud = (CrudPropertySheetItem) item;
                    try {
                        if (itemCrud.getBean().getClass().getDeclaredField(itemCrud.getOriginalName()).isAnnotationPresent(LongString.class)) {
                            return getTextAreaPropertyEditor(item);
                        }else if (itemCrud.getBean().getClass().getDeclaredField(itemCrud.getOriginalName()).isAnnotationPresent(Password.class)){
                            return getPasswordPropertyEditor(item);
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                PropertyEditor<?> defaultEditor = new DefaultPropertyEditorFactory().call(item);
                if (item instanceof CrudPropertySheetItem) {
                    CrudPropertySheetItem itemCrud = (CrudPropertySheetItem) item;
                    try {
                        if (itemCrud.getBean().getClass().getDeclaredField(itemCrud.getOriginalName()).isAnnotationPresent(Readonly.class)) {
                            defaultEditor.getEditor().setDisable(true);
                        }
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                }
                return defaultEditor;
            }


        };
    }

    // TODO Improve. Remove repeated code.
    private static AbstractPropertyEditor<String, PasswordField> getPasswordPropertyEditor(PropertySheet.Item item) {
        final PasswordField passwordField = new PasswordField();
        passwordField.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return new AbstractPropertyEditor<>(item, passwordField) {
            {
                CrudBeanPropertyUtils.enableAutoSelectAll(this.getEditor());
            }

            protected StringProperty getObservableValue() {
                return this.getEditor().textProperty();
            }

            public void setValue(String value) {
                this.getEditor().setText(value);
            }
        };
    }
    private static AbstractPropertyEditor<String, TextArea> getTextAreaPropertyEditor(PropertySheet.Item item) {
        CrudPropertySheetItem itemCrud = (CrudPropertySheetItem) item;
        int rows = 5;
        try {
            rows = itemCrud.getBean().getClass().getDeclaredField(itemCrud.getOriginalName()).getAnnotation(LongString.class).rows();
        }catch (NoSuchFieldException e) {

        }

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(19 * rows);
        //TODO implement height for the text area
        textArea.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return new AbstractPropertyEditor<>(item, textArea) {
            {
                CrudBeanPropertyUtils.enableAutoSelectAll(this.getEditor());
            }

            protected StringProperty getObservableValue() {
                return this.getEditor().textProperty();
            }

            public void setValue(String value) {
                this.getEditor().setText(value);
            }
        };
    }
}
