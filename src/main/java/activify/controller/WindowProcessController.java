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
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

            // Limpiar el contenedor antes de cargar nuevas actividades
            progresoContainer.getChildren().clear();

            // Obtener todas las actividades del usuario
            List<Activity> activities = activityService.getActivitiesByUserId(userId);

            if (activities != null) {
                for (Activity activity : activities) {
                    // Crear y agregar un nuevo VBox con los detalles de la actividad
                    HBox activityBox = createActivityBox(activity);
                    progresoContainer.getChildren().add(activityBox);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar actividades", "Ocurrió un error al intentar cargar las actividades del usuario.");
        }
    }


    private HBox createActivityBox(Activity activity) {
        HBox activityBox = new HBox();
        activityBox.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 5; -fx-border-color: grey;");
        activityBox.setSpacing(10);

        try {
            // Cargar la imagen desde src/main/resources/activify/view/images/user_icon.png
            InputStream imageStream = getClass().getResourceAsStream("/activify/view/images/user_icon.png");
            Image image = new Image(imageStream);
            ImageView activityImageView = new ImageView(image);
            activityImageView.setFitWidth(100);
            activityImageView.setFitHeight(100);

            // Crear un VBox para la imagen
            VBox imageBox = new VBox(activityImageView);
            imageBox.setStyle("-fx-alignment: center;");

            // Configurar los elementos de texto con los datos de la actividad usando Labels
            Label usernameLabel = new Label(activity.getUser().getName());
            usernameLabel.setStyle("-fx-font-size: 18px;");

            Label dateLabel = new Label(activity.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            dateLabel.setStyle("-fx-text-fill: gray;");

            Label titleLabel = new Label(activity.getTitle());
            titleLabel.setStyle("-fx-font-size: 22px;");

            Label distanceLabel = new Label("Distancia: " + String.format("%.2f km", activity.getDistance()));
            Label elevationLabel = new Label("Desnivel Positivo:  " + activity.getElevation() + " m");
            Label durationLabel = new Label("Tiempo: " + activity.getDuration().toString());

            HBox detailsBox = new HBox(10, distanceLabel, new Label("|"), elevationLabel, new Label("|"), durationLabel);

            // Crear los botones de editar y borrar
            Button editButton = new Button("Editar");
            editButton.setStyle("-fx-background-color: #fc5200; -fx-text-fill: white;");
            editButton.setOnAction(e -> handleEditActivity(activity));

            Button deleteButton = new Button("Borrar");
            deleteButton.setStyle("-fx-background-color: #007fc6; -fx-text-fill: white;");
            deleteButton.setOnAction(e -> handleDeleteActivity(activity));

            HBox buttonBox = new HBox(10, editButton, deleteButton);

            // Crear un VBox para el contenido de la actividad
            VBox contentBox = new VBox(5, usernameLabel, dateLabel, titleLabel, detailsBox, new Label(""), buttonBox);
            contentBox.setStyle("-fx-padding: 0 10 0 10;");

            // Agregar los VBoxs al HBox principal
            activityBox.getChildren().addAll(imageBox, contentBox);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar actividad", "Ocurrió un error al cargar los detalles de la actividad.");
        }

        return activityBox;
    }


    private void handleDeleteActivity(Activity activity) {
        try {
            activityService.deleteActivity(activity.getId());
            showAlert(Alert.AlertType.INFORMATION, "Actividad borrada", "La actividad se borró correctamente.");
            loadActivities();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al borrar la actividad", "Ocurrió un error al intentar borrar la actividad.");
        }
    }

    private void handleEditActivity(Activity activity) {
        try {
            // Cargar el archivo FXML de edición
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/EditActivity.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de edición
            EditActivityController controller = loader.getController();
            controller.setActivity(activity);
            controller.setActivityService(activityService);

            // Configurar la escena y la ventana
            Stage stage = new Stage();
            stage.setTitle("Editar Actividad");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recargar las actividades después de cerrar la ventana de edición
            loadActivities();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar la ventana de edición", "Ocurrió un error al intentar cargar la ventana de edición.");
        }
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