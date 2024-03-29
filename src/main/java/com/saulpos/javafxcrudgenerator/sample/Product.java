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

import com.saulpos.javafxcrudgenerator.annotations.Currency;
import com.saulpos.javafxcrudgenerator.annotations.*;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBeanImplementationSoftDelete;
import javafx.beans.property.*;

import java.beans.PropertyVetoException;
import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class Product extends AbstractBeanImplementationSoftDelete<Product> {

    @Search
    @DisplayOrder(orderValue = 100)
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> assignmentDay = new SimpleObjectProperty<>();
    @Readonly
    @DisplayOrder(orderValue = 2)
    private final SimpleStringProperty description = new SimpleStringProperty();
    @Password
    @DisplayOrder(orderValue = 3)
    private final SimpleStringProperty password = new SimpleStringProperty();
    @DisplayOrder(orderValue = 4)
    private final SimpleObjectProperty<Price> price = new SimpleObjectProperty<>();
    @DisplayOrder(orderValue = 5)
    private final SimpleStringProperty measuringUnit = new SimpleStringProperty();
    @DisplayOrder(orderValue = 6)
    private final SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currency = new SimpleObjectProperty<>();
    @DisplayOrder(orderValue = 7)
    private final SimpleBooleanProperty isAvailable = new SimpleBooleanProperty();
    @Search
    @DisplayOrder(orderValue = 8)
    private final SimpleObjectProperty<LocalDate> initializationDate = new SimpleObjectProperty<>();
    @Currency
    @DisplayOrder(orderValue = 9)
    private final SimpleDoubleProperty total = new SimpleDoubleProperty();
    @LongString(rows = 10)
    @Category(name = "Advanced")
    @DisplayOrder(orderValue = 10)
    private final SimpleStringProperty wideDescription = new SimpleStringProperty();
    @Ignore
    @DisplayOrder(orderValue = 11)
    private final SimpleStringProperty extraLongDescription = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalTime> startingTime = new SimpleObjectProperty<>();
    public Product() {
    }

    public LocalDateTime getAssignmentDay() {
        return assignmentDay.get();
    }

    public void setAssignmentDay(LocalDateTime assignmentDay) {
        this.assignmentDay.set(assignmentDay);
    }

    public ObjectProperty<LocalDateTime> assignmentDayProperty() {
        return assignmentDay;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public Price getPrice() {
        return price.get();
    }

    public void setPrice(Price price) {
        this.price.set(price);
    }

    public SimpleObjectProperty<Price> priceProperty() {
        return price;
    }

    public String getMeasuringUnit() {
        return measuringUnit.get();
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit.set(measuringUnit);
    }

    public SimpleStringProperty measuringUnitProperty() {
        return measuringUnit;
    }

    public com.saulpos.javafxcrudgenerator.sample.Currency getCurrency() {
        return currency.get();
    }

    public void setCurrency(com.saulpos.javafxcrudgenerator.sample.Currency currency) {
        this.currency.set(currency);
    }

    public SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currencyProperty() {
        return currency;
    }

    public boolean getIsAvailable() {
        return isAvailable.get();
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

    public SimpleBooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    public LocalDate getInitializationDate() {
        return initializationDate.get();
    }

    public void setInitializationDate(LocalDate initializationDate) {
        this.initializationDate.set(initializationDate);
    }

    public SimpleObjectProperty<LocalDate> initializationDateProperty() {
        return initializationDate;
    }

    public String getWideDescription() {
        return wideDescription.get();
    }

    public void setWideDescription(String wideDescription) {
        this.wideDescription.set(wideDescription);
    }

    public SimpleStringProperty wideDescriptionProperty() {
        return wideDescription;
    }

    public String getExtraLongDescription() {
        return extraLongDescription.get();
    }

    public void setExtraLongDescription(String extraLongDescription) {
        this.extraLongDescription.set(extraLongDescription);
    }

    public SimpleStringProperty extraLongDescriptionProperty() {
        return extraLongDescription;
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public LocalTime getStartingTime() {
        return startingTime.get();
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime.set(startingTime);
    }

    public SimpleObjectProperty<LocalTime> startingTimeProperty() {
        return startingTime;
    }

    @Override
    public void saveOrUpdate() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        super.saveOrUpdate();
        // just to show how it could be used. Ideally it should be saved into the database.
        final List allItems = CrudGeneratorSample.CUSTOM_DATA_PROVIDER.getAllItems(Product.class);
        if (!previouslySaved()) {
            allItems.add(this);
        }
        System.out.println("Product saved/updated");
    }

    @Override
    public void receiveChanges(Product currentBean) {
        // todo: improve
        this.setName(currentBean.getName());
        this.setCurrency(currentBean.getCurrency());
        this.setDescription(currentBean.getDescription());
        this.setTotal(currentBean.getTotal());
        this.setMeasuringUnit(currentBean.getMeasuringUnit());
        this.setExtraLongDescription(currentBean.getExtraLongDescription());
        this.setInitializationDate(currentBean.getInitializationDate());
        this.setIsAvailable(currentBean.getIsAvailable());
        this.setPrice(currentBean.getPrice());
        this.setWideDescription(currentBean.getWideDescription());
        this.setPassword(currentBean.getPassword());
    }

    @Override
    public boolean previouslySaved() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        return CrudGeneratorSample.CUSTOM_DATA_PROVIDER.getAllItems(Product.class).contains(this);
    }

    @Override
    public Product clone() {
        Product clonedProduct = new Product();
        clonedProduct.setName(this.getName());
        clonedProduct.setCurrency(this.getCurrency());
        clonedProduct.setDescription(this.getDescription());
        clonedProduct.setTotal(this.getTotal());
        clonedProduct.setMeasuringUnit(this.getMeasuringUnit());
        clonedProduct.setExtraLongDescription(this.getExtraLongDescription());
        clonedProduct.setInitializationDate(this.getInitializationDate());
        clonedProduct.setIsAvailable(this.getIsAvailable());
        clonedProduct.setPrice(this.getPrice());
        clonedProduct.setWideDescription(this.getWideDescription());
        clonedProduct.setPassword(this.getPassword());

        try {
            return deepClone(clonedProduct);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clonedProduct;
    }

    private Product deepClone(Product toClone) throws IOException, ClassNotFoundException {
        // Serialize the object
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(toClone);

        // Deserialize the object
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Product) ois.readObject();
    }

    @Override
    public void delete() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        super.delete();
        // just to show how it could be used. Ideally it should be saved into the database.
        final List allItems = CrudGeneratorSample.CUSTOM_DATA_PROVIDER.getAllItems(Product.class);
        allItems.remove(this);
        System.out.println("Product deleted; " + getName());
    }
}
