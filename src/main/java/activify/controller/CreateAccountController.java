package activify.controller;

import activify.model.User;
import activify.repo.UserRepository;
import activify.service.UserService;
import com.mysql.cj.xdevapi.SessionFactory;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class CreateAccountController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSubmit;

    private UserService userService;

    // Constructor sin argumentos
    public CreateAccountController() {
    }

    // Método para inicializar el controlador con un SessionFactory
    public void initialize(SessionFactory sessionFactory) {
        // Inicializa userService con una instancia de UserRepository
        UserRepository userRepository = new UserRepository((org.hibernate.SessionFactory) sessionFactory);
        this.userService = new UserService(userRepository);
    }

    @FXML
    void createAccount() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String email = "example@example.com";

        // Intenta crear una nueva cuenta
        User newUser = new User(username, email, password);

        userService.createUser(newUser);

        // Una vez creada la cuenta, abrir la ventana de actividades
        openWindowActivities();
    }


    @FXML
    private void handleButtonClick() {
        // Obtener la escena desde el botón btnSubmit
        Scene scene = btnSubmit.getScene();

        // Obtener el stage (ventana) actual desde la escena
        Stage currentStage = (Stage) scene.getWindow();

        // Aquí puedes realizar cualquier acción adicional con el stage actual
        // Por ejemplo, cerrar la ventana actual
        currentStage.close();
    }

    private void openWindowActivities() {
        try {
            // Cargar el archivo FXML de la ventana de actividades
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/WindowActivities.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de actividades
            WindowActivitiesController windowActivitiesController = loader.getController();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Crear la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Window Activities");

            // Mostrar la ventana
            stage.show();

            // Cerrar la ventana actual de creación de cuenta
            Stage currentStage = (Stage) btnSubmit.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(org.hibernate.SessionFactory sessionFactory) {
        // Crear un repositorio de usuario utilizando el SessionFactory
        UserRepository userRepository = new UserRepository(sessionFactory);

        // Inicializar el servicio de usuario con el repositorio creado
        this.userService = new UserService(userRepository);
    }
}
