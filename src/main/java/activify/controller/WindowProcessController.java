package activify.controller;

import activify.model.Activity;
import activify.repo.ActivityRepository;
import activify.service.ActivityService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

public class WindowProcessController {

    private ActivityService activityService;

    @FXML
    private TableView<Activity> activityTableView;

    @FXML
    private TableColumn<Activity, Integer> colId;

    @FXML
    private TableColumn<Activity, Double> colDistance;

    @FXML
    private TableColumn<Activity, String> colDuration;

    @FXML
    private TableColumn<Activity, Integer> colElevation;

    @FXML
    private TableColumn<Activity, String> colSport;

    @FXML
    private TableColumn<Activity, String> colDate;

    @FXML
    private TableColumn<Activity, String> colTitle;

    @FXML
    private Button TextActividades;

    @FXML
    public void initialize() {
        // Configurar y crear la instancia de SessionFactory
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Crear una instancia de ActivityRepository y pasarle el SessionFactory
        ActivityRepository activityRepository = new ActivityRepository(sessionFactory);

        // Crear una instancia de ActivityService y pasarle la instancia de ActivityRepository
        activityService = new ActivityService(activityRepository);

        // Asignar el controlador de eventos a los botones
        TextActividades.setOnAction(event -> handleButtonActivities());

        // Configurar las celdas de la tabla para mostrar los datos de la actividad
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colDistance.setCellValueFactory(cellData -> cellData.getValue().distanceProperty().asObject());
        colDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        colElevation.setCellValueFactory(cellData -> cellData.getValue().elevationProperty().asObject());
        colSport.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        colTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        // Cargar todas las actividades y mostrarlas en la tabla
        loadActivities();
    }

    private void loadActivities() {
        // Obtener todas las actividades del servicio
        List<Activity> activities = activityService.getAllActivities();
        if (activities != null) {
            // Agregar las actividades a la tabla
            activityTableView.getItems().addAll(activities);
        }
    }

    private void handleButtonActivities() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el TextActividades
            Stage stage = (Stage) TextActividades.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAutoClosingAlert("ERROR: Error al cargar la pantalla de datos de actividades.", Alert.AlertType.ERROR, Duration.seconds(5));
        }
    }

    public void showAutoClosingAlert(String message, Alert.AlertType alertType, Duration duration) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);

        String imagePath = "";

        switch (alertType) {
            case INFORMATION:
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsCheckCircleOutline2.png";
                break;
            case WARNING:
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsErrorOutlineRounded.png";
                break;
            case ERROR:
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsErrorOutlineRounded.png";
                break;
            case CONFIRMATION:
                imagePath = "/org/JavviFdeez/images/MaterialSymbolsCheckCircleOutline2.png";
                break;
        }

        // Crear el ImageView con el icono y el tamaño deseado
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        // Establecer el icono personalizado como el gráfico de la alerta
        alert.setGraphic(icon);

        Button closeButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        closeButton.setVisible(false);

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