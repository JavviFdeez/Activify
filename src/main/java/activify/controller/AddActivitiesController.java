package activify.controller;

import activify.model.Activity;
import activify.model.User;
import activify.repo.ActivityRepository;
import activify.repo.UserRepository;
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
    private UserService userService;

    @FXML
    private void initialize() {
        activityService = new ActivityService(new ActivityRepository(SessionFactoryUtil.getSessionFactory()));
        userService = new UserService(new UserRepository(SessionFactoryUtil.getSessionFactory())); // Inicializa el servicio de usuario
        updateTitle("Bicicleta");

        btnSave.setOnAction(event -> saveActivity());
        ButtonCanceled.setOnAction(event -> {
            try {
                cancelActivity();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        for (MenuItem item : ButtonSport.getItems()) {
            item.setOnAction(this::handleMenuItemAction);
        }

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
            double distance = Double.parseDouble(txtDistance.getText());
            int hours = Integer.parseInt(txtDuration.getText());
            int minutes = Integer.parseInt(txtDuration1.getText());
            int seconds = Integer.parseInt(txtDuration11.getText());
            int elevation = Integer.parseInt(txtElevation.getText());
            String sport = ButtonSport.getText();
            LocalDate date = datePicker.getValue();
            String title = txtTitle.getText();

            // Obtén el usuario actual
            User currentUser = userService.getCurrentUser();

            if (currentUser != null) {
                // Crear un objeto Time con los valores obtenidos
                Time duration = new Time(hours * 3600000L + minutes * 60000L + seconds * 1000L);

                // Crear una nueva actividad asignando el usuario actual
                Activity newActivity = new Activity(distance, duration, elevation, sport, date, title, currentUser);
                activityService.createActivity(newActivity);

                showAlert(Alert.AlertType.INFORMATION, "Actividad Guardada", "La actividad se ha guardado correctamente.");
                backToWindowActivities();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error al guardar actividad", "No se pudo obtener el usuario actual.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de formato", "Asegúrate de ingresar valores numéricos válidos para la distancia, elevación y duración.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al guardar actividad", "Ocurrió un error al intentar guardar la actividad.");
            e.printStackTrace();
        }
    }


    private void handleMenuItemAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedSport = menuItem.getText();
        ButtonSport.setText(selectedSport);
        updateTitle(selectedSport);
    }

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

    private void backToWindowActivities() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ButtonSport.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancelActivity() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cancelación");
        alert.setHeaderText("¿Estás seguro de que deseas cancelar?");
        alert.setContentText("Si cancelas, se perderán todos los cambios.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ButtonSport.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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