package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.annotations.Currency;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

public class Product implements AbstractBean {

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleStringProperty description = new SimpleStringProperty();

    private SimpleObjectProperty<Price> price = new SimpleObjectProperty<>();

    private SimpleStringProperty measuringUnit = new SimpleStringProperty();

    private SimpleObjectProperty<com.saulpos.javafxcrudgenerator.sample.Currency> currency = new SimpleObjectProperty<>();

    private SimpleBooleanProperty isAvailable = new SimpleBooleanProperty();

    private SimpleObjectProperty<Calendar> initializationDate = new SimpleObjectProperty<>();

    @Currency
    private SimpleDoubleProperty total = new SimpleDoubleProperty();
    @LongString(rows=5)
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

    public Calendar getInitializationDate() {
        return initializationDate.get();
    }

    public SimpleObjectProperty<Calendar> initializationDateProperty() {
        return initializationDate;
    }

    public void setInitializationDate(Calendar initializationDate) {
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

    @Override
    public Integer save() {
        System.out.println("Product saved");
        return null;
    }

    @Override
    public void update() {
        System.out.println("Product updated");

    }

    @Override
    public void saveOrUpdate() {
        System.out.println("Product saved/updated");
    }

    @Override
    public void delete() {
        System.out.println("Product deleted; " + getName());
    }
}
