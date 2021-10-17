package ClientCittadino;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CittadinoLogin implements Initializable {
    @FXML private TextField username;
    @FXML private TextField password;
    private static Stage stage1;


    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CittadinoLogin.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public void loginUtente(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void annulla() throws IOException {
        OperatoreSanitarioAPP.setRoot("cittadinoEventiAvversi");
    }
    public void login(){

    }
}