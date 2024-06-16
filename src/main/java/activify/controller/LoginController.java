package activify.controller;

import activify.ActivifyApp;
import activify.model.User;
import activify.repo.UserRepository;
import activify.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button ButtonGoToLogin;

    @FXML
    private Button buttonRegister;

    private UserService userService;


    @FXML
    private void initialize() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("activify-persistence-unit");
        UserRepository userRepository = new UserRepository(entityManagerFactory);
        txtUsername.setPromptText("Usuario *");
        txtPassword.setPromptText("Contraseña *");
        this.userService = new UserService(userRepository);
        ButtonGoToLogin.setOnAction(event -> login());
        buttonRegister.setOnAction(event -> openWindowCreateAccount());
    }

    @FXML
    private void login() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Llama al método login del UserService para verificar las credenciales
        User user = userService.login(username, password);

        // Verifica si el usuario es válido y las credenciales son correctas
        if (user != null) {
            // Aquí se abrirá la ventana de actividades si las credenciales son correctas
            openWindowActivities();
        } else {
            showErrorDialog("Credenciales incorrectas");
        }
        openWindowActivities();
    }

    @FXML
    public void openWindowActivities() {
        try {
            // Cargar archivo FXML
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

    @FXML
    public void openWindowCreateAccount() {
        try {
            // Cargar archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/CreateAccount.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de actividades
            CreateAccountController createAccountController = loader.getController();

            // Configurar la escena
            Scene scene = new Scene(root);

            // Crear la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Create Account");

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

    @FXML
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}