package activify.javvifdez.com;

import activify.javvifdez.com.Controller.*;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActivifyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Activify/src/main/resources/META-INF/LogIn.fxml"));
        loader.setController(new LogInController());
        Parent root = loader.load();

        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
