package com.saulpos.javafxcrudgenerator.view;

import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.annotations.Password;
import com.saulpos.javafxcrudgenerator.annotations.Readonly;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import jfxtras.scene.control.LocalTimePicker;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.lang.reflect.Field;
import java.time.*;
import java.util.HashMap;

import jfxtras.scene.control.LocalDateTimePicker;

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
                    if (getFields(itemCrud).get(itemCrud.getOriginalName()).isAnnotationPresent(LongString.class)) {
                        return getTextAreaPropertyEditor(item);
                    }else if (getFields(itemCrud).get(itemCrud.getOriginalName()).isAnnotationPresent(Password.class)){
                        return getPasswordPropertyEditor(item);
                    }
                    else if (java.time.LocalDateTime.class.equals (itemCrud.getPropertyDescriptor().getPropertyType())){
                        return getLocalDateTimePropertyEditor(item);
                    }
                    else if (java.time.LocalTime.class.equals( itemCrud.getPropertyDescriptor().getPropertyType())){
                        return getLocalTimePropertyEditor(item);
                    }
                }

                PropertyEditor<?> defaultEditor = new DefaultPropertyEditorFactory().call(item);
                if (item instanceof CrudPropertySheetItem) {
                    CrudPropertySheetItem itemCrud = (CrudPropertySheetItem) item;

                    if (getFields(itemCrud).get(itemCrud.getOriginalName()).isAnnotationPresent(Readonly.class)) {
                        defaultEditor.getEditor().setDisable(true);
                    }
                }
                return defaultEditor;
            }
        };
    }

    private static HashMap<String, Field> getFields(CrudPropertySheetItem itemCrud) {
        HashMap<String, Field> fields = new HashMap<>();
        for (Class<?> c = itemCrud.getBean().getClass(); c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()){
                fields.put(f.getName(), f);
            }
        }
        return fields;
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

    private static AbstractPropertyEditor<LocalTime, LocalTimePicker> getLocalTimePropertyEditor(PropertySheet.Item item) {
        LocalTimePicker timePicker = new LocalTimePicker();
        timePicker.setLocalTime(LocalTime.now());


        return new AbstractPropertyEditor<>(item, timePicker) {
            @Override
            public void setValue(LocalTime value) {
                if (value != null) {
                    timePicker.setLocalTime(value);
                }
            }

            @Override
            protected ObservableValue<LocalTime> getObservableValue() {
                return new ObservableValue<LocalTime>() {
                    @Override
                    public void addListener(ChangeListener<? super LocalTime> changeListener) {
                        timePicker.localTimeProperty().addListener((observableValue, oldValue, newValue) -> {
                            changeListener.changed(this, oldValue, newValue);
                        });
                    }

                    @Override
                    public void removeListener(ChangeListener<? super LocalTime> changeListener) {
                        timePicker.localTimeProperty().removeListener((InvalidationListener) changeListener);
                    }

                    @Override
                    public LocalTime getValue() {
                        return timePicker.getLocalTime();
                    }

                    @Override
                    public void addListener(InvalidationListener invalidationListener) {
                        timePicker.localTimeProperty().addListener((observableValue, oldValue, newValue) -> {
                            invalidationListener.invalidated(this);
                        });
                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {
                        timePicker.localTimeProperty().removeListener((InvalidationListener) invalidationListener);
                    }
                };
            }
        };
    }

    private static AbstractPropertyEditor<LocalDateTime, HBox> getLocalDateTimePropertyEditor(PropertySheet.Item item) {
        HBox hbox = new HBox();
        LocalDateTimePicker dateTimePicker = new LocalDateTimePicker();
        dateTimePicker.setLocalDateTime(LocalDateTime.now());

        hbox.getChildren().add(dateTimePicker);

        return new AbstractPropertyEditor<>(item, hbox) {
            @Override
            public void setValue(LocalDateTime value) {
                if (value != null) {
                    dateTimePicker.setLocalDateTime(value);
                }
            }

            @Override
            protected ObservableValue<LocalDateTime> getObservableValue() {
                return new ObservableValue<LocalDateTime>() {
                    @Override
                    public void addListener(ChangeListener<? super LocalDateTime> changeListener) {
                        dateTimePicker.localDateTimeProperty().addListener((observableValue, oldValue, newValue) -> {
                            changeListener.changed(this, oldValue, newValue);
                        });
                    }

                    @Override
                    public void removeListener(ChangeListener<? super LocalDateTime> changeListener) {
                        dateTimePicker.localDateTimeProperty().removeListener((InvalidationListener) changeListener);
                    }

                    @Override
                    public LocalDateTime getValue() {
                        return dateTimePicker.getLocalDateTime();
                    }

                    @Override
                    public void addListener(InvalidationListener invalidationListener) {
                        dateTimePicker.localDateTimeProperty().addListener((observableValue, oldValue, newValue) -> {
                            invalidationListener.invalidated(this);
                        });
                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {
                        dateTimePicker.localDateTimeProperty().removeListener((InvalidationListener) invalidationListener);
                    }
                };
            }
        };
    }

    private static AbstractPropertyEditor<String, TextArea> getTextAreaPropertyEditor(PropertySheet.Item item) {
        CrudPropertySheetItem itemCrud = (CrudPropertySheetItem) item;
        int rows = 5;
        try {
            rows = itemCrud.getBean().getClass().getDeclaredField(itemCrud.getOriginalName()).getAnnotation(LongString.class).rows();
        }catch (Exception e) {

        }

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(19 * rows);
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
