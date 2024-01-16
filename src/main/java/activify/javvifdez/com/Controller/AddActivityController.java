package activify.javvifdez.com.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class AddActivityController {

    @FXML
    private TextArea TextAreaDistance;

    @FXML
    private TextArea TextAreaHour;

    @FXML
    private TextArea TextAreaSecond;

    @FXML
    private TextArea TextAreaUnevenness;

    @FXML
    private void handleKeyTyped(KeyEvent event) {
        String character = event.getCharacter();

        // Verificar si el carácter es un dígito
        if (!character.matches("\\d")) {
            event.consume();
        }
    }
}

