package activify.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowActivitiesController {
    @FXML
    private Button buttonAddActivity;

    @FXML
    private Button buttonProgreso;

    @FXML
    private void initialize() {
        // Verifica que los botones se hayan inyectado correctamente
        if (buttonAddActivity != null && buttonProgreso != null) {
            buttonAddActivity.setOnAction(event -> openAddActivityWindow());
            buttonProgreso.setOnAction(event -> handleButtonProgress());
        } else {
            System.err.println("");
        }
    }

    @FXML
    private void openAddActivityWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/AddActivity.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) buttonAddActivity.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar la ventana de añadir actividad", "Ocurrió un error al intentar cargar la ventana de añadir actividad.");
        }
    }

    @FXML
    private void handleButtonProgress() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowProcess.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) buttonProgreso.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar la ventana de progreso", "Ocurrió un error al intentar cargar la ventana de progreso.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}