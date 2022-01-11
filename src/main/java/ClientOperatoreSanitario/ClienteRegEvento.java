package ClientOperatoreSanitario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;
import java.net.URL;
import java.util.ResourceBundle;


public class ClienteRegEvento implements Initializable {

    //pannelli
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;

    //elementi del primo pannello
    @FXML private TextField TFUser;
    @FXML private PasswordField PFPassword;
    @FXML private Label LBErrPass;
    @FXML private Label LBErrUser;

    //elementi del secondo pannello
    @FXML private Spinner<String> SPCentro;
    @FXML private ComboBox<String> CBEvento;
    @FXML private Rating RTValutazione;
    @FXML private TextArea TANote;
    @FXML private Label LBCaratteri;

    //funzione che inizializza il ComboBox con le varie scelte
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CBEvento.getItems().addAll("Mal di testa", "Febbre", "Dolori muscolari e articolari", "Linfoadenopatia", "Tachicardia", "Crisi ipertensiva");
        CBEvento.setVisibleRowCount(3);
        CBEvento.setEditable(false);
        CBEvento.setPromptText("--seleziona--");

        //Serve per limitare ad un massimo 256 caratteri il testo che si inserisce
        TANote.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 256 ? change : null));
    }

    //funzione che controlla i dati di login e nel caso di risposta affermativa procede nella pagina di scelta del centro
    public void confermaLogin(ActionEvent actionEvent) {

        if (controlloDB()) {
            pane1.setVisible(false);
            TFUser.clear();
            PFPassword.clear();
            LBErrPass.setVisible(false);
            LBErrUser.setVisible(false);
            pane2.setVisible(true);
        }
        else {
            //a seconda dell'errore si mostra il label associato
            LBErrPass.setVisible(true);
            LBErrUser.setVisible(true);
        }

    }

    //funzione che controlla la correttezza dei dati di login
    private boolean controlloDB() {
        String userid = TFUser.getText().trim();
        String password = PFPassword.getText().trim();

        //ricerca nel DB...

        return true;
    }

    //vai avanti nella pagina di segnalazione dell'evento
    public void confermaEvento(ActionEvent actionEvent) {

        //RTValutazione.getRating()             comando per prendere il numero di stelle selezionate
    }

    //il tasto BTAnnulla1 torna indietro alla pagina di scelta delle varie operazioni che l'utente (Cittadino) può svolgere
    public void annulla1(ActionEvent actionEvent) {

    }

    //il tasto BTAnnulla2 torna indietro al pannello 1
    public void annulla2(ActionEvent actionEvent) {
        pane2.setVisible(false);
        //SPCentro.cancelEdit();
        CBEvento.valueProperty().set(null);
        RTValutazione.setRating(0);
        TANote.clear();
        pane1.setVisible(true);
    }

    //funzione che comunica il numero di caratteri inseriti nella TextArea TANote
    public void numeroCaratteri(KeyEvent keyEvent) {
        LBCaratteri.setText(String.valueOf(TANote.getText().length()));
    }
}
