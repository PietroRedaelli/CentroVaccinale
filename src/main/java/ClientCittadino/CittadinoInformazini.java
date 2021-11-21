package ClientCittadino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CittadinoInformazini {

    @FXML Button informazioni;

    public void indietro(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }
}
