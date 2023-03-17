package com.saulpos.javafxcrudgenerator.model.dao;

import com.saulpos.javafxcrudgenerator.annotations.Ignore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
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
        Active, Modified, Deleted
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
        setLastModificationTime(LocalDateTime.now());
        modify();
    }

    @Override
    public void update() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        setBeanStatus(BeanStatus.Modified);
        setLastModificationTime(LocalDateTime.now());
        modify();
        AbstractBeanImplementationSoftDelete newActiveObject = this.clone();
        AbstractBeanImplementationSoftDelete.removeId(newActiveObject); // force it to be a new object.
        newActiveObject.setBeanStatus(BeanStatus.Active);
        newActiveObject.save();

    }

    @Override
    public void saveOrUpdate() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {
        if (previouslySaved()){
            update();
        }else{
            setBeanStatus(BeanStatus.Active);
            setLastModificationTime(LocalDateTime.now());
            save();
        }
    }

    @Override
    public void save() throws PropertyVetoException, IOException, URISyntaxException, ClassNotFoundException {

    }

    public static void removeId(Object object){
        try {
            object.getClass().getDeclaredMethod("setId", int.class).invoke(object, 0);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
