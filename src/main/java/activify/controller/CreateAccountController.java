package activify.controller;

import activify.ActivifyApp;
import activify.model.User;
import activify.repo.UserRepository;
import activify.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;


public class CreateAccountController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button ButtonRegister;

    @FXML
    private Button ButtonGoToLogin;

    @FXML
    private Button btnSubmit;

    private UserService userService;

    // Constructor sin argumentos
    public CreateAccountController() {
    }

    // Método para inicializar el controlador con un EntityManagerFactory
    public void initialize(EntityManagerFactory entityManagerFactory) {
        UserRepository userRepository = new UserRepository(entityManagerFactory);
        txtUsername.setPromptText("Usuario *");
        txtPassword.setPromptText("Contraseña *");
        this.userService = new UserService(userRepository);
        this.ButtonRegister.setOnAction(event -> createAccount());
        this.ButtonGoToLogin.setOnAction(event -> openWindowLogin());
    }

    @FXML
    private void createAccount() {
        try {
            // Obtener el nombre de usuario y la contraseña
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            // Crear un nuevo usuario con los datos ingresados
            User newUser = new User(username, password);

            // Guardar el nuevo usuario en la base de datos
            userService.createUser(newUser);

            // Cerrar la ventana actual de creación de cuenta
            Stage currentStage = (Stage) btnSubmit.getScene().getWindow();
            currentStage.close();

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        // Cargar el archivo FXML de la ventana de inicio de sesión
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/Login.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la ventana de inicio de sesión
        LoginController loginController = loader.getController();

        // Configurar la escena
        Scene scene = new Scene(root);

        // Crear la nueva ventana
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesión");

        // Cargar el ícono de la aplicación en esta ventana
        ActivifyApp.setAppIcon(stage);

        // Mostrar la ventana
        stage.show();

        // Cerrar la ventana actual de creación de cuenta
        Node source = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void openWindowLogin() {
        try {
            // Cargar archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/Login.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de actividades
            LoginController loginController = loader.getController();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Crear la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Log In");

            // Cargar el ícono de la aplicación en esta ventana
            ActivifyApp.setAppIcon(stage);

            // Mostrar la ventana
            stage.show();

            // Cerrar la ventana actual de inicio de sesión
            Stage currentStage = (Stage) txtUsername.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}