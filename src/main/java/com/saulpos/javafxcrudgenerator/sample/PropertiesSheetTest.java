package com.saulpos.javafxcrudgenerator.sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.layout.VBox;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;
import org.controlsfx.property.editor.DefaultPropertyEditorFactory;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.ArrayList;
import java.util.List;

public class PropertiesSheetTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Product bean = new Product();
        Price p1 = new Price();
        p1.setValue(10.0);
        p1.setDiscount(.3);
        bean.setPrice(p1);
        Product bean2 = new Product();
        bean2.setName("Name");
        ObservableList<PropertySheet.Item> properties = BeanPropertyUtils.getProperties(bean);
        PropertySheet propertySheet = new PropertySheet(properties);
        //propertySheet.setMode(PropertySheet.Mode.CATEGORY);
        propertySheet.setSearchBoxVisible(true);

        propertySheet.setModeSwitcherVisible(false);
        DefaultPropertyEditorFactory defaultPropertyEditorFactory = new DefaultPropertyEditorFactory();
        propertySheet.setPropertyEditorFactory(new Callback<PropertySheet.Item, PropertyEditor<?>>() {
            @Override
            public PropertyEditor<?> call(PropertySheet.Item param) {
                if(param.getName().equals("price")){
                    List<Price> ageList = new ArrayList<>();
                    ageList.add(p1);
                    Price p2 = new Price();
                    p2.setValue(20.0);
                    p2.setDiscount(.2);
                    ageList.add(p2);

                    return Editors.createChoiceEditor(param,ageList);
                }
                return defaultPropertyEditorFactory.call(param);
            }
        });

        VBox vBox = new VBox(propertySheet);
        Button newButton = new Button("OK");
        newButton.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent actionEvent) {
                                      propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(bean2));

                                  }
                              });
        Scene scene = new Scene(vBox);
        Button newButton2 = new Button("OK2");
        newButton2.setOnAction(new EventHandler<ActionEvent>() {
                                   @Override
                                   public void handle(ActionEvent actionEvent) {
                                       System.out.println(bean2.getDescription());
                                   }
                               });
        vBox.getChildren().add(newButton);
        vBox.getChildren().add(newButton2);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
