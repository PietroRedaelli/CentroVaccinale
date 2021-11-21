package ClientCittadino;

import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppCittadino  extends Application {
    private static Scene scene;
    private static Stage stage1;
    @Override
    public void start(Stage stage) throws Exception {
        stage1 = stage;
        scene = new Scene(loadFXML("cittadinoMainMenu.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("Menu");
        stage1.show();
    }
    static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

