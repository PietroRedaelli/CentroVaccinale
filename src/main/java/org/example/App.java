package org.example;

import ServerPackage.CentroVaccinale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage1;

    @Override
    public void start(Stage stage) throws IOException {
        stage1 = stage;
        scene = new Scene(loadFXML("operatoreSceltaReg"));
        stage1.setScene(scene);
        stage1.setTitle("CentroVaccinale");
        stage1.show();
    }

    static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}