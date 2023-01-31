package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import com.saulpos.javafxcrudgenerator.presenter.CrudPresenter;
import com.saulpos.javafxcrudgenerator.CrudGenerator;
import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.NodeConstructor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;

public class CrudGeneratorSample extends Application {

    public static final AbstractDataProvider CUSTOM_DATA_PROVIDER = new AbstractDataProvider() {

        private List allItems = new ArrayList();

        {
            Product p1 = new Product();
            p1.setName("Apple");
            p1.setIsAvailable(true);
            p1.setDescription("Description here");
            allItems.add(p1);
            Product p2 = new Product();
            p2.setName("Banana");
            p2.setIsAvailable(false);
            allItems.add(p2);
            Product p3 = new Product();
            p3.setName("Ananas");
            p3.setIsAvailable(true);
            //p3.setInitializationDate(new LocalDate);
            allItems.add(p3);
        }

        @Override
        public List getAllItems(Class clazz) {
            if (Product.class.equals(clazz)) {
                return allItems;
            } else if (Price.class.equals(clazz)) {
                final ArrayList<Price> prices = new ArrayList<>();
                Price p1 = new Price();
                p1.setDiscount(.2);
                p1.setValue(35.);
                p1.setStartingDate(Calendar.getInstance().getTime());
                p1.setEndingDate(Calendar.getInstance().getTime());
                prices.add(p1);
                Price p2 = new Price();
                p2.setDiscount(.2);
                p2.setValue(40.);
                p2.setStartingDate(Calendar.getInstance().getTime());
                p2.setEndingDate(Calendar.getInstance().getTime());
                prices.add(p2);
                return prices;
            } else if (clazz.isEnum()) {
                return new ArrayList(EnumSet.allOf(clazz));
            }

            return new ArrayList();
        }

        @Override
        public List getAllItems(Class clazz, AbstractBean filter) {
            // It is just to show how it works. It is not a real implementation of the filtering
            // If you are using Database, criteria should go down to database.
            List allItems = getAllItems(clazz);
            if (!(filter instanceof Product)) {
                return allItems;
            }
            Product objFilter = (Product) filter;
            List filtered = new ArrayList();
            for (Object obj : allItems) {
                if (!(obj instanceof Product)) {
                    continue;
                }
                Product objProduct = (Product) obj;
                boolean isOK = true;
                isOK &= (objFilter.getName() == null) || (objProduct.getName() != null && objProduct.getName().contains(objFilter.getName()));
                isOK &= (objFilter.getInitializationDate() == null) || (objProduct.getInitializationDate() != null && objProduct.getInitializationDate().equals(objFilter.getInitializationDate()));
                if (isOK) {
                    filtered.add(obj);
                }
            }
            return filtered;
        }

        @Override
        public boolean isRegisteredClass(Class clazz) {
            return clazz.equals(Price.class);
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CrudGeneratorParameter crudGeneratorParameter = new CrudGeneratorParameter();
        NodeConstructor customButtonConstructor = new NodeConstructor() {
            @Override
            public Node generateNode(Object... name) {
                Button customButton = new Button("Custom Button");
                customButton.setPrefWidth(crudGeneratorParameter.getButtonWidth());
                customButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Custom Button");
                    }
                });
                return customButton;
            }
        };

        crudGeneratorParameter.setClazz(Product.class);
        crudGeneratorParameter.setDataProvider(CUSTOM_DATA_PROVIDER);

        crudGeneratorParameter.getExtraButtonsConstructor().add(customButtonConstructor);
        CrudGenerator<Product> crudGenerator = new CrudGenerator<>(crudGeneratorParameter);

        stage.setTitle("Crud Generator Sample Application");

        StackPane root = new StackPane();

        CrudPresenter crud = crudGenerator.generate();
        root.getChildren().add(crud.getView().getMainView());

        stage.setScene(new Scene(root, 1160, 640));
        stage.show();
    }
}
