package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.annotations.*;
import com.saulpos.javafxcrudgenerator.annotations.Currency;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBeanImplementationSoftDelete;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyVetoException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.io.*;


public class Product extends AbstractBeanImplementationSoftDelete<Product> {

    @Search
    @DisplayOrder(orderValue = 100)
    private SimpleStringProperty name = new SimpleStringProperty();

    @Readonly
    @DisplayOrder(orderValue = 2)
    private SimpleStringProperty description = new SimpleStringProperty();

    @Password
    @DisplayOrder(orderValue = 3)
    private SimpleStringProperty password = new SimpleStringProperty();

    @DisplayOrder(orderValue = 4)
    private SimpleObjectProperty<Price> price = new SimpleObjectProperty<>();

    @DisplayOrder(orderValue = 5)
    private SimpleStringProperty measuringUnit = new SimpleStringProperty();

    @DisplayOrder(orderValue = 6)
    private SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currency = new SimpleObjectProperty<>();

    @DisplayOrder(orderValue = 7)
    private SimpleBooleanProperty isAvailable = new SimpleBooleanProperty();

    @Search
    @DisplayOrder(orderValue = 8)
    private SimpleObjectProperty<LocalDate> initializationDate = new SimpleObjectProperty<>();

    @Currency
    @DisplayOrder(orderValue = 9)
    private SimpleDoubleProperty total = new SimpleDoubleProperty();
    @LongString(rows=10)
    @Category(name = "Advanced")
    @DisplayOrder(orderValue = 10)
    private SimpleStringProperty wideDescription = new SimpleStringProperty();

    @Ignore
    @DisplayOrder(orderValue = 11)
    private SimpleStringProperty extraLongDescription = new SimpleStringProperty();

    public Product() {
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Price getPrice() {
        return price.get();
    }

    public SimpleObjectProperty<Price> priceProperty() {
        return price;
    }

    public void setPrice(Price price) {
        this.price.set(price);
    }

    public String getMeasuringUnit() {
        return measuringUnit.get();
    }

    public SimpleStringProperty measuringUnitProperty() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit.set(measuringUnit);
    }

    public com.saulpos.javafxcrudgenerator.sample.Currency getCurrency() {
        return currency.get();
    }

    public SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currencyProperty() {
        return currency;
    }

    public void setCurrency(com.saulpos.javafxcrudgenerator.sample.Currency currency) {
        this.currency.set(currency);
    }

    public boolean getIsAvailable() {
        return isAvailable.get();
    }

    public SimpleBooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }

    public LocalDate getInitializationDate() {
        return initializationDate.get();
    }

    public SimpleObjectProperty<LocalDate> initializationDateProperty() {
        return initializationDate;
    }

    public void setInitializationDate(LocalDate initializationDate) {
        this.initializationDate.set(initializationDate);
    }

    public String getWideDescription() {
        return wideDescription.get();
    }

    public SimpleStringProperty wideDescriptionProperty() {
        return wideDescription;
    }

    public void setWideDescription(String wideDescription) {
        this.wideDescription.set(wideDescription);
    }

    public String getExtraLongDescription() {
        return extraLongDescription.get();
    }

    public SimpleStringProperty extraLongDescriptionProperty() {
        return extraLongDescription;
    }

    public void setExtraLongDescription(String extraLongDescription) {
        this.extraLongDescription.set(extraLongDescription);
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

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public void save() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        super.save();
        // just to show how it could be used. Ideally it should be saved into the database.
        final List allItems = CrudGeneratorSample.CUSTOM_DATA_PROVIDER.getAllItems(Product.class);
        if (!previouslySaved()){
            allItems.add(this);
        }

        System.out.println("Product saved");
    }

    @Override
    public void update() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        System.out.println("Product updated");

    }

    @Override
    public void saveOrUpdate() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        super.saveOrUpdate();
        // just to show how it could be used. Ideally it should be saved into the database.
        final List allItems = CrudGeneratorSample.CUSTOM_DATA_PROVIDER.getAllItems(Product.class);
        if (!previouslySaved()){
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
    public void modify() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        update();
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
        return  clonedProduct;
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
        if (allItems.contains(this)){
            allItems.remove(this);
        }
        System.out.println("Product deleted; " + getName());
    }
}
