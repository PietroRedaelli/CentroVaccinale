package ClientCittadino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CittadinoMainMenu {
    @FXML Button log;
    @FXML Button registrazione;
    @FXML Button informazioni;
    private static Stage stage1;

    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CittadinoMainMenu.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public void apriScenaLogin(ActionEvent actionEvent) throws IOException {
        CittadinoMainMenu.setRoot("cittadinoLogin");
        Scene scene = new Scene(new FXMLLoader(CittadinoLogin.class.getResource("operatoreRegCentro.fxml")).load());
    }

    public void apriScenaRegistrazione(ActionEvent actionEvent) throws IOException{
        CittadinoMainMenu.setRoot("cittadinoRegistrazione");
        Scene scene = new Scene(new FXMLLoader(CittadinoRegistrazione.class.getResource("operatoreRegCentro.fxml")).load());
    }
    public void informazioniShow(ActionEvent actionEvent) throws IOException {
        CittadinoMainMenu.setRoot("cittadinoRegistrazione");
        Scene scene = new Scene(new FXMLLoader(CittadinoInformazini.class.getResource("operatoreRegCentro.fxml")).load());
    }
}
