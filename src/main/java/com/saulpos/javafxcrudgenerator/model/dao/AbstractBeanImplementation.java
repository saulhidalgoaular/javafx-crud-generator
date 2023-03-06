package com.saulpos.javafxcrudgenerator.model.dao;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public abstract class AbstractBeanImplementation<T extends AbstractBeanImplementation > implements AbstractBean<T> {
    @Override
    public T clone() {
        return null;
    }

    enum BeanStatus{
        Active, Modified, Deleted
    }

    private SimpleObjectProperty<LocalDateTime> lastModificationTime = new SimpleObjectProperty<>();

    private SimpleObjectProperty<LocalDateTime> creationTime = new SimpleObjectProperty<>();

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

    public BeanStatus getBeanStatus() {
        return beanStatus.get();
    }

    public SimpleObjectProperty<BeanStatus> beanStatusProperty() {
        return beanStatus;
    }

    public void setBeanStatus(BeanStatus beanStatus) {
        this.beanStatus.set(beanStatus);
    }
}
