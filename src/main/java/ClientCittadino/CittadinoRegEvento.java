package ClientCittadino;

import ClientCittadino.AppCittadino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

//LUCA: salvataggio dell'evento avverso e implementa qui il metodo visualizzaInfoCentroVaccinale nella classe server
//al massimo qui lo richiamo e nella parte server la implemento
//a riga 215

public class CittadinoRegEvento implements Initializable {

    //variabili
    protected boolean LoginCheck = false;

    //pannelli
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;

    //elementi del primo pannello
    @FXML private TextField TFUser;
    @FXML private PasswordField PFPassword;
    @FXML private Label LBErr;

    //elementi del secondo pannello
    @FXML private Spinner<String> SPCentro;
    @FXML private ComboBox<String> CBEvento;
    @FXML private Rating RTValutazione;
    @FXML private TextArea TANote;
    @FXML private Label LBCaratteri;

    public CittadinoRegEvento() {
    }

    //funzione che inizializza il ComboBox con le varie scelte
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pane1.setVisible(true);
        pane2.setVisible(false);

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
            if(LBErr.isVisible())
                LBErr.setVisible(false);
            TFUser.clear();
            PFPassword.clear();
            pane2.setVisible(true);
            LoginCheck = true;
        }
        else {
            //a seconda dell'errore si mostra il label associato
            LBErr.setVisible(true);
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
    public void confermaCentro(ActionEvent actionEvent) {

        //RTValutazione.getRating()             comando per prendere il numero di stelle selezionate
    }

    //il tasto BTAnnulla1 torna indietro alla pagina di scelta delle varie operazioni che l'utente (Cittadino) può svolgere
    public void annulla1(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }

    //il tasto BTAnnulla2 torna indietro al pannello 1
    public void annulla2(ActionEvent actionEvent) throws IOException {
        //aprire un Alert per uscire e fare anche il logout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sicuro di voler uscire?\nTutti i dati riportati per l'evento avverso andranno persi");
        alert.setTitle("Uscita Registrazione evento avverso");
        alert.setContentText("Verrà eseguito il LOGOUT dal tuo account.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            LoginCheck = false;
            pane2.setVisible(false);
            SPCentro.cancelEdit();
            CBEvento.valueProperty().set(null);
            RTValutazione.setRating(0);
            TANote.clear();
            pane1.setVisible(true);
            AppCittadino.setRoot("cittadinoMainMenu.fxml");
        }
    }

    //funzione che comunica il numero di caratteri inseriti nella TextArea TANote
    public void numeroCaratteri(KeyEvent keyEvent) {
        LBCaratteri.setText(String.valueOf(TANote.getText().length()));
    }
}