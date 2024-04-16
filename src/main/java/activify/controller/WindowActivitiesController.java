package activify.controller;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowActivitiesController {

    @FXML
    private Button btnAddActivity;

    @FXML
    private void initialize() {
        // Inicializar el controlador
        btnAddActivity.setOnAction(event -> handleAddActivity());
    }

    private void handleAddActivity() {
        try {
            // Cargar el archivo FXML de la ventana AddActivities
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/AddActivities.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana AddActivities
            AddActivitiesController addActivitiesController = loader.getController();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Crear una nueva ventana para AddActivities
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Activities");

            // Mostrar la ventana AddActivities
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

