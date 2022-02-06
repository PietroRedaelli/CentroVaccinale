package serverCV;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * La classe AppServer ha come scopo quello di avviare il server.
 */
public class AppServer extends Application {

    private static Stage stage1;

    @Override
    public void start(Stage stage) throws IOException {
        stage1 = stage;
        Scene scene = new Scene(loadFXML("server.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("Server");
        stage1.setResizable(false);
        stage1.show();
    }

    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppServer.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        Server.disconnetti();
        System.exit(0);
    }
}