package activify;

import activify.controller.CreateAccountController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class ActivifyApp extends Application {
    private EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Inicializar EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory("activify-persistence-unit");

        // Cargar archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/CreateAccount.fxml"));
        Parent root = loader.load();

        // Inicializar controlador con EntityManagerFactory
        CreateAccountController controller = loader.getController();
        controller.initialize(entityManagerFactory);

        // Establecer escena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Establecer ícono de la aplicación con un tamaño específico
        Image appIcon = new Image(getClass().getResourceAsStream("/activify/view/images/LogoActivify.png"));
        primaryStage.getIcons().add(appIcon);

        // Establecer título de la ventana
        primaryStage.setTitle("Activify App");

        // Maximizar la ventana
        primaryStage.setMaximized(true);

        // Mostrar ventana
        primaryStage.show();
    }

    // Método para cargar y configurar el ícono de la aplicación
    public static void setAppIcon(Stage stage) {
        Image appIcon = new Image(ActivifyApp.class.getResourceAsStream("/activify/view/images/LogoActivify.png"));
        stage.getIcons().add(appIcon);
    }

    @Override
    public void stop() {
        // Cerrar EntityManagerFactory cuando la aplicación se detiene
        entityManagerFactory.close();
    }
}