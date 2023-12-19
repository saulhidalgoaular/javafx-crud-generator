module com.saulpos.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires fontawesomefx;
    requires jakarta.persistence;
    requires jfxtras.controls;
    requires highlightfx;

    exports com.saulpos.javafxcrudgenerator;
    exports com.saulpos.javafxcrudgenerator.sample;
    exports com.saulpos.javafxcrudgenerator.annotations;
    exports com.saulpos.javafxcrudgenerator.model.dao;
    exports com.saulpos.javafxcrudgenerator.model;
    exports com.saulpos.javafxcrudgenerator.presenter;
    exports com.saulpos.javafxcrudgenerator.view;
    opens com.saulpos.javafxcrudgenerator;

}