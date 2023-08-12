module com.saulpos.javafxcrudgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires fontawesomefx;
    requires jakarta.persistence;
    requires jfxtras.controls;

    opens com.saulpos.javafxcrudgenerator to javafx.fxml;
    exports com.saulpos.javafxcrudgenerator.sample;

}