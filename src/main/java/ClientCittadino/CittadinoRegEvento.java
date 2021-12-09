package ClientCittadino;

import ServerPackage.CentroVaccinale;
import ServerPackage.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;

//LUCA: salvataggio dell'evento avverso e implementa qui il metodo visualizzaInfoCentroVaccinale nella classe server
//al massimo qui lo richiamo e nella parte server la implemento
//a riga 215

public class CittadinoRegEvento implements Initializable {

    //variabili
    protected boolean LoginCheck = false;
    private static final ServerInterface si = AppCittadino.si;
    private CentroVaccinale nome_centro = null;
    public static Cittadino cittadino = null;

    //pannelli
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;

    //elementi del primo pannello
    @FXML private TextField TFUser;
    @FXML private PasswordField PFPassword;
    @FXML private Label LBErr;
    @FXML private Button BTConferma1;
    @FXML private Button BTAnnulla1;

    //elementi del secondo pannello
    @FXML public Label L_nomeCentro;
    @FXML public Label L_ID_Vacc;
    @FXML private ComboBox<String> CBEvento;
    @FXML private Rating RTValutazione;
    @FXML private TextArea TANote;
    @FXML private Label LBCaratteri;
    @FXML private Button BTConferma2;
    @FXML private Button BTAnnulla2;

    //funzione che inizializza la finestra principale
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(cittadino == null){
            pane1.setVisible(true);
            pane2.setVisible(false);
        }else{
            pane1.setVisible(false);
            pane2.setVisible(true);
            impostaDati();
        }

        //setCheckBox del tipo di Evento
        CBEvento.getItems().addAll("Mal di testa", "Febbre", "Dolori muscolari e articolari", "Linfoadenopatia", "Tachicardia", "Crisi ipertensiva");
        CBEvento.setVisibleRowCount(10);
        CBEvento.setEditable(false);
        CBEvento.setPromptText("--seleziona tipo di evento--");

        //Serve per limitare ad un massimo 256 caratteri il testo che si inserisce
        TANote.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 256 ? change : null));
    }

    private void getNomeCentro() {
        try {
            nome_centro = si.cercaCentroVaccinale_CF(cittadino.getCodiceFiscale());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //funzione che controlla i dati di login e nel caso di risposta affermativa procede nella pagina di scelta del centro
    public void confermaLogin(ActionEvent actionEvent) {

        if (controlloDB()) {
            impostaDati();
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

    //setLabel
    private void impostaDati() {
        getNomeCentro();
        L_nomeCentro.setText(nome_centro.getNomeCentro());
        L_ID_Vacc.setText(String.valueOf(cittadino.getIdVacc()));
    }

    //funzione che controlla la correttezza dei dati di login
    private boolean controlloDB() {
        String userid = TFUser.getText().trim();
        String password = PFPassword.getText().trim();
        try {
            if(!si.controllaCittadinoUserId(userid)){
                LBErr.setText("User ID inesistente!");
                return false;
            }
            cittadino = si.controllaCittadinoLogin(userid,password);
            if(cittadino == null){
                LBErr.setText("Password sbagliata!");
                return false;
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    //vai avanti nella pagina di segnalazione dell'evento
    public void confermaCentro(ActionEvent actionEvent) {
        if(controlliCampi()){//verifica dei campi
            if(controlloEventAvverso()){//verifica della presenza di un evento nel DB
                //carica i risultati sul DB
                EventoAvverso evento = getEvento();
                try {
                    si.inserisciEventiAvversi(evento);
                    System.out.println("Aggiunto : "+ evento);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    private boolean controlloEventAvverso() {
        return true;
    }

    private boolean controlliCampi() {
        return true;
    }

    private EventoAvverso getEvento() {
        return new EventoAvverso(nome_centro.getNomeCentro(), cittadino.codiceFiscale, CBEvento.getValue(), (int) RTValutazione.getRating(),TANote.getText());
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
            cittadino = null;
            LoginCheck = false;
            pane2.setVisible(false);
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