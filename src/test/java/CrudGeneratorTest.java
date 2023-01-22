import com.saulpos.javafxcrudgenerator.CrudGenerator;
import com.saulpos.javafxcrudgenerator.CrudGeneratorParameter;
import com.saulpos.javafxcrudgenerator.NodeConstructor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CrudGeneratorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CrudGeneratorParameter crudGeneratorParameter = new CrudGeneratorParameter();
        NodeConstructor customButtonConstructor = new NodeConstructor() {
            @Override
            public Node generateNode(Object... name) {
                Button customButton = new Button("Custom Button");
                customButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Custom Button");
                    }
                });
                return customButton;
            }
        };

        crudGeneratorParameter.getExtraButtonsConstructor().add(customButtonConstructor);
        CrudGenerator crudGenerator = new CrudGenerator(crudGeneratorParameter);

        stage.setTitle("Hello World!");

        StackPane root = new StackPane();
        root.getChildren().add(crudGenerator.generate(Product.class));
        stage.setScene(new Scene(root, 800, 640));
        stage.show();
    }
}
