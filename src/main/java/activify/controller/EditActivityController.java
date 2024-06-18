package activify.controller;

import activify.model.Activity;
import activify.service.ActivityService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditActivityController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> sportComboBox;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField elevationField;

    @FXML
    private TextField durationField;

    private Activity activity;
    private ActivityService activityService;

    @FXML
    private void initialize() {
        // Inicializar el ComboBox de deporte con las opciones disponibles
        sportComboBox.setItems(FXCollections.observableArrayList(
                "Bicicleta", "Carrera a pie", "Natación"));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        populateFields();
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    private void populateFields() {
        titleField.setText(activity.getTitle());
        datePicker.setValue(activity.getDate());
        sportComboBox.setValue(activity.getSport());
        distanceField.setText(String.valueOf(activity.getDistance()));
        elevationField.setText(String.valueOf(activity.getElevation()));
        durationField.setText(activity.getDuration().toString());
    }

    @FXML
    private void handleSave() {
        try {
            activity.setTitle(titleField.getText());
            activity.setDate(datePicker.getValue());
            activity.setSport(sportComboBox.getValue());
            activity.setDistance(Double.parseDouble(distanceField.getText()));
            activity.setElevation(Integer.parseInt(elevationField.getText()));

            // Convertir la entrada de duración a Time
            String durationText = durationField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(durationText, formatter);
            Time time = Time.valueOf(localTime);
            activity.setDuration(time);

            activityService.updateActivity(activity);

            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
            showAlert(Alert.AlertType.INFORMATION, "Actividad actualizada", "La actividad se actualizo correctamente.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error en los datos", "Por favor, ingresa valores numéricos válidos para distancia y desnivel.");
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Error en la duración", "Por favor, ingresa la duración en el formato HH:mm:ss.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al guardar la actividad", "Ocurrió un error al intentar guardar los cambios.");
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}