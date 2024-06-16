package activify.controller;

import activify.ActivifyApp;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class WindowActivitiesController {
    @FXML
    private Button buttonAddActivity;

    @FXML
    private Button TextProgreso;
    @FXML
    private void initialize() {
        handleController();
        TextProgreso.setOnAction(event -> handleButtonProgress());
    }

    private void handleController() {
        buttonAddActivity.setOnAction(event -> openAddActivityWindow());
    }

    // Método para abrir la ventana AddActivity
    @FXML
    private void openAddActivityWindow() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/AddActivity.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) buttonAddActivity.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAutoClosingAlert("ERROR: Error al cargar la pantalla de datos de Añadir actividad.", AlertType.ERROR, Duration.seconds(5));
        }
    }

    private void handleButtonProgress() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowProcess.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) buttonAddActivity.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAutoClosingAlert("ERROR: Error al cargar la pantalla de datos de progreso.", AlertType.ERROR, Duration.seconds(5));
        }
    }

    public enum AlertType {
        INFORMATION,
        WARNING,
        ERROR,
        SUCCESS
    }

    public void showAutoClosingAlert(String message, AlertType type, Duration duration) {
        Alert.AlertType alertType = null;
        String cssStyle = "";
        String imagePath = "";
        

        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();


        // Establecer la altura mínima y máxima para controlar el tamaño vertical
        dialogPane.setMinHeight(80);
        dialogPane.setMaxHeight(150);

        dialogPane.getScene().getRoot().setStyle(" -fx-background-radius: 15; -fx-background-color: transparent;");
        dialogPane.setStyle("-fx-font-family: 'Roboto';");
        alert.getDialogPane().getScene().setFill(null);

        alert.getDialogPane().setPrefSize(500, 1);

        alert.show();

        // Configurar la duración de la alerta
        PauseTransition delay = new PauseTransition(duration);
        delay.setOnFinished(event -> alert.close());
        delay.play();
    }
}