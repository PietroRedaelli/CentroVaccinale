package ClientCittadino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

/**
 * La classe ha lo scopo di connettere il cittadino al server.
 */
public class CittadinoLoading {

    @FXML private Label LBConnessione;

    public void connessioneServer(ActionEvent actionEvent) throws IOException, InterruptedException {
        LBConnessione.setVisible(true);
        boolean connessioneAccettata = AppCittadino.connessione_server();
        if (connessioneAccettata) {
            LBConnessione.setText("Connessione Accettata");
            //ipotetica attesa di un secondo
            AppCittadino.setRoot("cittadinoMainMenu.fxml");
        } else {
            LBConnessione.setText("Connessione al server non riuscita. Riprovare tra poco.");
        }
    }
}