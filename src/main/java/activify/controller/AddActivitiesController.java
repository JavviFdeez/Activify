package activify.controller;

import activify.model.Activity;
import activify.repo.ActivityRepository;
import activify.service.ActivityService;
import activify.service.UserService;
import activify.util.SessionFactoryUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;

public class AddActivitiesController {

    @FXML
    private TextField txtDistance;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtDuration1;

    @FXML
    private TextField txtDuration11;

    @FXML
    private TextField txtElevation;

    @FXML
    private DatePicker datePicker;

    @FXML
    private MenuButton ButtonSport;

    @FXML
    private TextField txtTitle;

    @FXML
    private Button btnSave;

    @FXML
    private Button ButtonCanceled;

    private ActivityService activityService;

    @FXML
    private void initialize() {
        // Inicializar el servicio de actividad con el repositorio y SessionFactory adecuados
        activityService = new ActivityService(new ActivityRepository(SessionFactoryUtil.getSessionFactory()));
        updateTitle("Bicicleta");
        // Configurar el evento para el botón Guardar
        btnSave.setOnAction(event -> saveActivity());
        ButtonCanceled.setOnAction(event -> {
            try {
                cancelActivity();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Configurar eventos para los elementos del menú de deportes
        for (MenuItem item : ButtonSport.getItems()) {
            item.setOnAction(this::handleMenuItemAction);
        }

        // Configuración adicional de la interfaz de usuario
        setupUI();
    }

    private void setupUI() {
        txtDuration.setPromptText("   h");
        txtDuration1.setPromptText("   min");
        txtDuration11.setPromptText("   s");
    }

    @FXML
    void saveActivity() {
        try {
            // Obtener los valores ingresados por el usuario desde los campos de texto y otros controles
            double distance = Double.parseDouble(txtDistance.getText());
            int hours = Integer.parseInt(txtDuration.getText());
            int minutes = Integer.parseInt(txtDuration1.getText());
            int seconds = Integer.parseInt(txtDuration11.getText());
            int elevation = Integer.parseInt(txtElevation.getText());
            String sport = ButtonSport.getText();
            LocalDate date = datePicker.getValue();
            String title = txtTitle.getText();

            // Obtener el ID del usuario actual
            UserService userService = new UserService();
            int userId = userService.getCurrentUserId();

            // Crear una nueva actividad
            Time duration = new Time(hours, minutes, seconds);  // Crear un objeto Time con los valores obtenidos

            Activity newActivity = new Activity(distance, duration, elevation, sport, date, title, userId);

            // Guardar la nueva actividad utilizando el servicio de actividad
            activityService.createActivity(newActivity);

            // Mostrar una alerta de éxito
            showAlert(Alert.AlertType.INFORMATION, "Actividad Guardada", "La actividad se ha guardado correctamente.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de formato", "Asegúrate de ingresar valores numéricos válidos para la distancia, elevación y duración.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al guardar actividad", "Ocurrió un error al intentar guardar la actividad.");
            e.printStackTrace();
        }
    }


    // Método para manejar el evento de selección de MenuItem
    private void handleMenuItemAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedSport = menuItem.getText();
        ButtonSport.setText(selectedSport);
        updateTitle(selectedSport);
    }

    // Actualizar el título basado en el deporte seleccionado
    private void updateTitle(String selectedSport) {
        switch (selectedSport) {
            case "Bicicleta":
                txtTitle.setText("Ruta en Bicicleta");
                break;
            case "Carrera a pie":
                txtTitle.setText("Carrera a pie");
                break;
            case "Natación":
                txtTitle.setText("Natación");
                break;
            default:
                txtTitle.setText("");
                break;
        }
    }

    @FXML
    void cancelActivity() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cancelación");
        alert.setHeaderText("¿Estás seguro de que deseas cancelar?");
        alert.setContentText("Si cancelas, se perderán todos los cambios.");

        // Manejar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Usuario confirmó la cancelación, navegar a WindowActivities.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el emailTextField
            Stage stage = (Stage) ButtonSport.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Método para mostrar una alerta
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}