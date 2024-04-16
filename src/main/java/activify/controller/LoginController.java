package activify.controller;

import activify.model.User;
import activify.repo.UserRepository;
import activify.service.UserService;
import com.mysql.cj.xdevapi.SessionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.io.IOException;


public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private UserService userService;


    // Constructor para inicializar el controlador
    public LoginController(SessionFactory sessionFactory) {
        // Inicializa userService con una instancia de UserRepository
        UserRepository userRepository = new UserRepository((org.hibernate.SessionFactory) sessionFactory);
        this.userService = new UserService(userRepository);
    }



    @FXML
    void login(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Intenta iniciar sesión utilizando el servicio de usuario
        User user = userService.login(username, password);

        if (user != null) {
            // Aquí se abrirá la ventana de actividades si las credenciales son correctas
            openWindowActivities();
        } else {
            showErrorDialog("Credenciales incorrectas");
        }
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

            // Cerrar la ventana actual de inicio de sesión
            Stage currentStage = (Stage) txtUsername.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void openLoginWindow() {
        try {
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
            stage.setTitle("Inicio de Sesión");

            // Mostrar la ventana
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}