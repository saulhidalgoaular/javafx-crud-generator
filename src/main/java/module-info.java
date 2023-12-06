module com.saulpos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires fontawesomefx;
    requires jakarta.persistence;
    requires jfxtras.controls;

    exports com.saulpos.javafxcrudgenerator;
    exports com.saulpos.javafxcrudgenerator.sample;
    opens com.saulpos.javafxcrudgenerator;

}