package activify.javvifdez.com.Controller;

import activify.javvifdez.com.DatabaseManager;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    @FXML
    private TextArea TextAreaUser;

    @FXML
    private TextArea TextAreaPassword;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Button ButtonRegister;

    // Método para manejar el evento del botón de inicio de sesión
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = TextAreaUser.getText();
        String password = TextAreaPassword.getText();

        // Verificar en la base de datos si el usuario y la contraseña son válidos
        boolean userExists = DatabaseManager.checkUser(username, password);

        if (userExists) {
            // El usuario existe, abre la ventana de registro
            handleRegisterButton(event);
        } else {
            // El usuario no existe, muestra un mensaje de error
            showAlert("Usuario no encontrado", "El usuario no existe. Regístrate para crear una cuenta.");
        }
    }

    // Método para manejar el evento del botón de registro
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        // Cargar la ventana CreateAccount.fxml
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/activify/javvifdez/com/View/CreateAccount.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar un mensaje de alerta
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
