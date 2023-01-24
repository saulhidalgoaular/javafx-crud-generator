package com.saulpos.javafxcrudgenerator.sample;

import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import com.saulpos.javafxcrudgenerator.annotations.LongString;
import com.saulpos.javafxcrudgenerator.model.dao.AbstractBean;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

public class Product implements AbstractBean {

    private SimpleStringProperty name;

    private SimpleStringProperty description;

    private Price price;

    private SimpleStringProperty measuringUnit;

    private Currency currency;

    private SimpleBooleanProperty isAvailable;

    private SimpleObjectProperty<Calendar> initializationDate;

    @LongString(rows=5)
    private SimpleStringProperty wideDescription;

    @Ignore
    private SimpleStringProperty extraLongDescription;

    public Product() {
    }

    public SimpleObjectProperty<Calendar> getInitializationDate() {
        return initializationDate;
    }

    public void setInitializationDate(SimpleObjectProperty<Calendar> initializationDate) {
        this.initializationDate = initializationDate;
    }

    public SimpleBooleanProperty isAvailable() {
        return isAvailable;
    }

    public void setAvailable(SimpleBooleanProperty available) {
        isAvailable = available;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public SimpleStringProperty getDescription() {
        return description;
    }

    public void setDescription(SimpleStringProperty description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public SimpleStringProperty getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(SimpleStringProperty measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public SimpleStringProperty getExtraLongDescription() {
        return extraLongDescription;
    }

    public void setExtraLongDescription(SimpleStringProperty extraLongDescription) {
        this.extraLongDescription = extraLongDescription;
    }

    @Override
    public Integer save() {
        System.out.println("Product saved into database");
        return null;
    }

    @Override
    public void update() {
        System.out.println("Product updated into database");

    }

    @Override
    public void saveOrUpdate() {
        System.out.println("Product saved/updated into database");
    }

    @Override
    public void delete() {
        System.out.println("Product deleted into database");
    }
}
