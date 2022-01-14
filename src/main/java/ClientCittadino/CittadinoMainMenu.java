package ClientCittadino;

import ClientOperatoreSanitario.OperatoreSceltaCentro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CittadinoMainMenu {
    @FXML Button log;
    @FXML Button registrazione;
    @FXML Button informazioni;

    public void apriScenaLogin(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoRegEvento.fxml");
    }

    public void apriScenaRegistrazione(ActionEvent actionEvent) throws IOException{
        AppCittadino.setRoot("cittadinoRegistrazione.fxml");
    }
    public void informazioniShow(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoSceltaCentro.fxml");
    }
}
