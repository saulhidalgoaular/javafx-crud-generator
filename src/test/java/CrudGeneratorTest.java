import com.saulpos.javafxcrudgenerator.CrudGenerator;
import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CrudGeneratorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CrudGeneratorParameter crudGeneratorParameter = new CrudGeneratorParameter();
        CrudGenerator crudGenerator = new CrudGenerator(crudGeneratorParameter);

        stage.setTitle("Hello World!");

        StackPane root = new StackPane();
        root.getChildren().add(crudGenerator.generate(Product.class));
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}
