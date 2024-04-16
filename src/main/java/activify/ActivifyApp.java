package activify;

import activify.controller.CreateAccountController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class ActivifyApp extends Application {
    private static final String PERSISTENCE_UNIT_NAME = "activify-persistence-unit";
    private static EntityManagerFactory entityManagerFactory;
    private static SessionFactory sessionFactory;


    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        launch(args);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/activify/view/fxml/CreateAccount.fxml"));
        Parent root = loader.load();
        CreateAccountController controller = loader.getController();
        controller.initialize(sessionFactory);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
