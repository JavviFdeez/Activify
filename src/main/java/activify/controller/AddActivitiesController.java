package activify.controller;

import activify.model.Activity;
import activify.service.ActivityService;
import javafx.fxml.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddActivitiesController {

    @FXML
    private TextField txtDistance;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtElevation;

    @FXML
    private TextField txtSport;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtRouteType;

    @FXML
    private Button btnSave;

    private ActivityService activityService;

    public AddActivitiesController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @FXML
    private void initialize() {
        btnSave.setOnAction(event -> saveActivity());
    }

    @FXML
    void saveActivity() {
        try {
            // Obtener los valores ingresados por el usuario desde los campos de texto y otros controles
            double distance = Double.parseDouble(txtDistance.getText());
            String duration = txtDuration.getText();
            int elevation = Integer.parseInt(txtElevation.getText());
            String sport = txtSport.getText();
            LocalDate date = datePicker.getValue();
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            String routeType = txtRouteType.getText();

            // Crear una nueva actividad
            Activity newActivity = new Activity(distance, duration, elevation, sport, date, title, description, routeType);

            // Guardar la nueva actividad utilizando el servicio de actividad
            activityService.createActivity(newActivity);

            showAlert(Alert.AlertType.INFORMATION, "Actividad Guardada", "La actividad se ha guardado correctamente.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de formato", "Asegúrate de ingresar valores numéricos válidos para la distancia y la elevación.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al guardar actividad", "Ocurrió un error al intentar guardar la actividad.");
            e.printStackTrace();
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
