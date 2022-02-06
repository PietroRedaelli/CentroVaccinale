package ClientOperatoreSanitario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

/**
 * La classe OperatoreLoading permette al ClientOperatoreSanitario di effettuare la connessione
 * al server.
 * @author Pietro
 * @version 1.0
 */
public class OperatoreLoading {

    @FXML private Label LBConnessione;

    public void connessioneServer(ActionEvent actionEvent) throws IOException, InterruptedException {
        LBConnessione.setVisible(true);
        boolean connessioneAccettata = OperatoreSanitarioAPP.connessione_server();
        if (connessioneAccettata) {
            LBConnessione.setText("Connessione Accettata");
            //ipotetica attesa di un secondo
            OperatoreSanitarioAPP.setRoot("operatoreSceltaReg.fxml");
        } else {
            LBConnessione.setText("Connessione al server non riuscita. Riprovare tra poco.");
        }
    }
}