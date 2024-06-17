package activify.controller;

import activify.model.Activity;
import activify.model.User;
import activify.repo.ActivityRepository;
import activify.repo.UserRepository;
import activify.service.ActivityService;
import activify.service.UserService;
import activify.util.SessionFactoryUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WindowProcessController {

    private ActivityService activityService;
    private UserService userService;

    @FXML
    private Button buttonActividades;

    @FXML
    private VBox progresoContainer;

    @FXML
    public void initialize() {
        // Configurar y crear la instancia de SessionFactory
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

        // Crear instancias de ActivityRepository y UserRepository
        ActivityRepository activityRepository = new ActivityRepository(sessionFactory);
        UserRepository userRepository = new UserRepository(sessionFactory);

        // Crear instancias de ActivityService y UserService
        activityService = new ActivityService(activityRepository);
        userService = new UserService(userRepository);

        // Asignar el controlador de eventos al botón
        buttonActividades.setOnAction(event -> handleButtonActivities());

        // Cargar todas las actividades del usuario autenticado y mostrarlas en el contenedor
        loadActivities();
    }

    private void loadActivities() {
        try {
            // Obtener el ID del usuario autenticado
            int userId = userService.getCurrentUserId();

            // Obtener todas las actividades del usuario
            List<Activity> activities = activityService.getActivitiesByUserId(userId);

            if (activities != null) {
                for (Activity activity : activities) {
                    // Crear y agregar un nuevo VBox con los detalles de la actividad
                    VBox activityBox = createActivityBox(activity);
                    progresoContainer.getChildren().add(activityBox);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar actividades", "Ocurrió un error al intentar cargar las actividades del usuario.");
        }
    }

    private VBox createActivityBox(Activity activity) {
        VBox activityBox = new VBox();
        activityBox.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 5; -fx-border-color: grey;");

        try {
            // Cargar la imagen desde src/main/resources/activify/view/images/user_icon.png
            InputStream imageStream = getClass().getResourceAsStream("/activify/view/images/user_icon.png");
            if (imageStream != null) {
                Image image = new Image(imageStream);
                ImageView activityImageView = new ImageView(image);
                activityImageView.setFitWidth(50);
                activityImageView.setFitHeight(50);

                // Configurar el nombre de usuario con tamaño de letra 18px
                Label usernameLabel = new Label(activity.getUser().getName());
                usernameLabel.setStyle("-fx-font-size: 18px;");

                // Configurar la fecha en color gris
                Label dateLabel = new Label(activity.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                dateLabel.setStyle("-fx-text-fill: grey;");

                // Configurar el título con tamaño de letra 22px
                Label titleLabel = new Label(activity.getTitle());
                titleLabel.setStyle("-fx-font-size: 22px;");

                // Configurar los textos y datos de distancia, desnivel positivo y tiempo
                Label distanceTextLabel = new Label("Distancia");
                Label elevationTextLabel = new Label("Desnivel Positivo");
                Label durationTextLabel = new Label("Tiempo");

                Label distanceValueLabel = new Label(String.format("%.2f km", activity.getDistance()));
                Label elevationValueLabel = new Label(activity.getElevation() + " m");
                Label durationValueLabel = new Label(activity.getDuration().toString());

                // Configurar un HBox para contener los textos y valores en una fila
                HBox detailsHBox = new HBox(10);
                detailsHBox.getChildren().addAll(
                        new VBox(distanceTextLabel, distanceValueLabel),
                        new VBox(elevationTextLabel, elevationValueLabel),
                        new VBox(durationTextLabel, durationValueLabel)
                );

                // Configurar un VBox para contener todos los elementos de texto a la derecha de la imagen
                VBox textVBox = new VBox(5);
                textVBox.getChildren().addAll(usernameLabel, dateLabel, titleLabel, detailsHBox);

                // Configurar un HBox para contener la imagen y el VBox de textos
                HBox mainHBox = new HBox(10);
                mainHBox.getChildren().addAll(activityImageView, textVBox);

                // Agregar el HBox principal al VBox de la actividad
                activityBox.getChildren().add(mainHBox);
            } else {
                // Manejar el caso donde imageStream es null (recurso no encontrado)
                showAlert(Alert.AlertType.ERROR, "Error al cargar actividad", "No se encontró la imagen del usuario.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar actividad", "Ocurrió un error al cargar los detalles de la actividad.");
        }

        return activityBox;
    }


    private void handleButtonActivities() {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
            Parent root = loader.load();

            // Obtener el escenario actual desde el buttonActividades
            Stage stage = (Stage) buttonActividades.getScene().getWindow();

            // Establecer la nueva escena en el escenario
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier error de carga del archivo FXML
            showAlert(Alert.AlertType.ERROR, "Error al cargar la ventana de actividades.", "Ocurrió un error al intentar cargar la ventana de actividades.");
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