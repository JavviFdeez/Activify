package activify.javvifdez.com.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddActivityController {

    @FXML
    private Pane rootPane;

    @FXML
    private Button ButtonSport;

    @FXML
    private TextArea TextAreaTitulo;

    @FXML
    private TextArea TextAreaDistance;

    @FXML
    private TextArea TextAreaHour;

    @FXML
    private TextArea TextAreaMin;

    @FXML
    private TextArea TextAreaSecond;

    @FXML
    private TextArea TextAreaUnevenness;

    @FXML
    private Button ButtonCanceled;

    @FXML
    private DatePicker DatePicker;

    private Stage currentStage;

    @FXML
    private void handleKeyTyped(KeyEvent event) {
        String character = event.getCharacter();

        // Verificar si el carácter es un dígito
        if (!character.matches("\\d")) {
            event.consume();
        }
    }

    @FXML
    private void initialize() {
        // Vincular el método al evento clic del botón "CANCELAR"
        ButtonCanceled.setOnAction(this::handleCancelButton);
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    private void handleCancelButton(javafx.event.ActionEvent actionEvent) {
        try {
            // Cargar la nueva vista (WindowActivities.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/javvifdez/com/View/WindowActivities.fxml"));
            Parent root = loader.load();

            // Crear el nuevo controlador
            WindowActivitiesController windowActivitiesController = loader.getController();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Establecer la nueva escena en el Stage principal
            currentStage.setScene(scene);

            // Mostrar el Stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
