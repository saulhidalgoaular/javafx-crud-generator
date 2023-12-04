package com.saulpos.javafxcrudgenerator.model.dao;

import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import jakarta.persistence.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractBeanImplementationSoftDelete<T extends AbstractBeanImplementationSoftDelete> implements AbstractBean<T> {
    @Override
    public T clone() {
        return null;
    }

    public enum BeanStatus{
        Active, Deleted
    }

    @Ignore
    private SimpleIntegerProperty id = new SimpleIntegerProperty();

    @Id
    @GeneratedValue
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    @Ignore
    private SimpleObjectProperty<LocalDateTime> lastModificationTime = new SimpleObjectProperty<>();

    @Ignore
    private SimpleObjectProperty<LocalDateTime> creationTime = new SimpleObjectProperty<>();

    @Ignore
    private SimpleObjectProperty<BeanStatus> beanStatus = new SimpleObjectProperty<>();

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> lastModificationTimeProperty() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime.set(lastModificationTime);
    }

    public LocalDateTime getCreationTime() {
        return creationTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> creationTimeProperty() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime.set(creationTime);
    }

    @Enumerated(EnumType.STRING)
    public BeanStatus getBeanStatus() {
        return beanStatus.get();
    }

    public SimpleObjectProperty<BeanStatus> beanStatusProperty() {
        return beanStatus;
    }

    public void setBeanStatus(BeanStatus beanStatus) {
        this.beanStatus.set(beanStatus);
    }

    public AbstractBeanImplementationSoftDelete() {
        setCreationTime(LocalDateTime.now());
        setBeanStatus(BeanStatus.Active);
    }

    @Override
    public void delete() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        setBeanStatus(BeanStatus.Deleted);
        saveOrUpdate();
    }

    @Override
    public void saveOrUpdate() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        setLastModificationTime(LocalDateTime.now());
    }

    public void removeId(){
        setId(0);
    }
}
