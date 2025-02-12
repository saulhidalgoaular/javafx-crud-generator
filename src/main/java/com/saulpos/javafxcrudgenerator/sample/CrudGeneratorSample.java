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
package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.CrudGenerator;
import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.model.Function;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractDataProvider;
import com.saulpos.javafxcrudgenerator.presenter.CrudPresenter;
import com.saulpos.javafxcrudgenerator.view.CustomButton;
import com.saulpos.javafxcrudgenerator.view.NodeConstructor;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;

public class CrudGeneratorSample extends Application {

    public static final AbstractDataProvider CUSTOM_DATA_PROVIDER = new AbstractDataProvider() {

        private final List allItems = new ArrayList();

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
        public List getAllItems(Class clazz, AbstractBean filter, SearchType type) {
            // It is just to show how it works. It is not a real implementation of the filtering
            // If you are using Database, criteria should go down to database.
            List allItems = getAllItems(clazz);
            if (!(filter instanceof Product objFilter)) {
                return allItems;
            }
            List filtered = new ArrayList();
            for (Object obj : allItems) {
                if (!(obj instanceof Product objProduct)) {
                    continue;
                }
                boolean isOK = true;
                isOK &= (objFilter.getName() == null) || (objProduct.getName() != null && objProduct.getName().toLowerCase().contains(objFilter.getName().toLowerCase()));
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

        @Override
        public void registerClass(Class clazz) {

        }

        @Override
        public List<Object[]> getItems(String query) {
            return null;
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CrudGeneratorParameter crudGeneratorParameter = new CrudGeneratorParameter();
        crudGeneratorParameter.setCurrentLocale(new Locale("en", "US"));
        NodeConstructor customButtonConstructor = new NodeConstructor() {
            @Override
            public Node generateNode(Object... name) {
                Button customButton = new Button();
                Label icon = GlyphsDude.createIconLabel(FontAwesomeIcon.STAR, crudGeneratorParameter.translate("custom.button"), "20px", "10px", ContentDisplay.LEFT);
                customButton.setGraphic(icon);
                customButton.setPrefWidth(crudGeneratorParameter.getButtonWidth());
                return customButton;
            }
        };

        Function customButtonFunction = new Function() {
            @Override
            public Object[] run(Object[] params) throws Exception {
                Product productBeingEdited = (Product) params[0];
                System.out.println(productBeingEdited.getName());
                return null;
            }
        };

        crudGeneratorParameter.addCustomButton(new CustomButton(customButtonConstructor, customButtonFunction, true));

        crudGeneratorParameter.setClazz(Product.class);
        crudGeneratorParameter.setDataProvider(CUSTOM_DATA_PROVIDER);
        crudGeneratorParameter.setBeforeSave(new Function() {
            @Override
            public Object[] run(Object[] params) throws Exception {
                //throw new Exception("You can't save");
                return null;
            }
        });

        CrudGenerator<Product> crudGenerator = new CrudGenerator<>(crudGeneratorParameter);

        stage.setTitle(crudGeneratorParameter.translate("window.title"));

        StackPane root = new StackPane();

        CrudPresenter crud = crudGenerator.generate();
        root.getChildren().add(crud.getView().getMainView());

        stage.setScene(new Scene(root, 1160, 640));
        stage.show();
    }
}
