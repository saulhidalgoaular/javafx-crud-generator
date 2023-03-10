package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.annotations.*;
import com.saulpos.javafxcrudgenerator.annotations.Currency;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBeanImplementation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyVetoException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.io.*;


public class Product extends AbstractBeanImplementation<Product> {

    @Search
    private SimpleStringProperty name = new SimpleStringProperty();

    @Readonly
    private SimpleStringProperty description = new SimpleStringProperty();

    @Password
    private SimpleStringProperty password = new SimpleStringProperty();

    private SimpleObjectProperty<Price> price = new SimpleObjectProperty<>();

    private SimpleStringProperty measuringUnit = new SimpleStringProperty();

    private SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currency = new SimpleObjectProperty<>();

    private SimpleBooleanProperty isAvailable = new SimpleBooleanProperty();

    @Search
    private SimpleObjectProperty<LocalDate> initializationDate = new SimpleObjectProperty<>();

    @Currency
    private SimpleDoubleProperty total = new SimpleDoubleProperty();
    @LongString(rows=10)
    @Category(name = "Advanced")
    private SimpleStringProperty wideDescription = new SimpleStringProperty();

    @Ignore
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
        super.update();
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
