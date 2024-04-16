package activify.controller;

import activify.model.Activity;
import activify.repo.ActivityRepository;
import activify.service.ActivityService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    private TableColumn<Activity, String> colDescription;

    @FXML
    private TableColumn<Activity, String> colRouteType;

    @FXML
    public void initialize() {
        // Configurar y crear la instancia de SessionFactory
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Crear una instancia de ActivityRepository y pasarle el SessionFactory
        ActivityRepository activityRepository = new ActivityRepository(sessionFactory);

        // Crear una instancia de ActivityService y pasarle la instancia de ActivityRepository
        activityService = new ActivityService(activityRepository);

        // Configurar las celdas de la tabla para mostrar los datos de la actividad
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colDistance.setCellValueFactory(cellData -> cellData.getValue().distanceProperty().asObject());
        colDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        colElevation.setCellValueFactory(cellData -> cellData.getValue().elevationProperty().asObject());
        colSport.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        colTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colRouteType.setCellValueFactory(cellData -> cellData.getValue().routeTypeProperty());

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
}