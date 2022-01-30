package ClientCittadino;

import ClientOperatoreSanitario.CentroVaccinale;
import Grafics.ConfirmBoxEventoAvverso;
import ServerPackage.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.Rating;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

//LUCA: salvataggio dell'evento avverso e implementa qui il metodo visualizzaInfoCentroVaccinale nella classe server
//al massimo qui lo richiamo e nella parte server la implemento
//a riga 215

public class CittadinoRegEvento implements Initializable {

    //variabili
    protected boolean LoginCheck = false;
    private static final ServerInterface si = AppCittadino.si;
    private CentroVaccinale nome_centro = null;
    private static Cittadino cittadino = null;

    //pannelli
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;

    //elementi del primo pannello
    @FXML private TextField TFUser;
    @FXML private PasswordField PFPassword;
    @FXML private Label LBErr;

    //elementi del secondo pannello
    @FXML private Label L_nomeCentro;
    @FXML private Label L_ID_Vacc;
    @FXML private ComboBox<String> CBEvento;
    @FXML private Rating RTValutazione;
    @FXML private Label Err_sev;
    @FXML private TextArea TANote;
    @FXML private Label LBCaratteri;

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
        LBErr.setText("");
        if (controlloLoginDB()) {
            impostaDati();
            pane1.setVisible(false);
            if(LBErr.isVisible()){
                LBErr.setText("");
                LBErr.setVisible(false);
            }
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
    private boolean controlloLoginDB() {
        String userid = TFUser.getText().trim();
        String password = PFPassword.getText().trim();
        if(userid.equals("") || password.equals("")){
            LBErr.setText(" Campi mancanti !");
            return false;
        }
        try {
            if(!si.controllaCittadinoUserId(userid)){
                LBErr.setText(" User ID inesistente ! ");
                return false;
            }
            cittadino = si.controllaCittadinoLogin(userid,password);
            if(cittadino == null){
                LBErr.setText(" Password sbagliata ! ");
                return false;
            }

        } catch (RemoteException e) {
            LBErr.setText("Errore di connessione col server. Riavviare l'applicazione.");
            return false;
        }
        System.out.println("Cittadino effettua login come: "+cittadino.getCodiceFiscale());
        return true;
    }

    //vai avanti nella pagina di segnalazione dell'evento
    public void confermaCentro(ActionEvent actionEvent) {
        if(controlloCampi()){//verifica dei campi
            EventoAvverso evento = getEvento();
            boolean conferma = ConfirmBoxEventoAvverso.start(evento);
            if(conferma){
                CBEvento.valueProperty().set(null);
                RTValutazione.setRating(0);
                TANote.clear();
            }

        }
    }

    private boolean controlloCampi() {
        if(CBEvento.getValue() == null){
            CBEvento.setPromptText("Campo mancante!");
            if(RTValutazione.getRating() == 0){
                Err_sev.setVisible(true);
                Err_sev.setTextFill(Color.RED);
                Err_sev.setText("Campo mancante!");
                return false;
            }
            return false;
        }

        if(RTValutazione.getRating() == 0){
            Err_sev.setVisible(true);
            Err_sev.setTextFill(Color.RED);
            Err_sev.setText("Campo mancante!");
            return false;
        }

        Err_sev.setVisible(false);
        return true;
    }

    private EventoAvverso getEvento() {
        return new EventoAvverso(nome_centro.getID(), nome_centro.getNomeCentro(), cittadino.getIdVacc(), cittadino.getCodiceFiscale(), CBEvento.getValue(), (int) RTValutazione.getRating(),TANote.getText());
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
            System.out.println(cittadino.getCodiceFiscale()+ " effettua logout");
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

    public static void setCittadino(Cittadino citt){
        cittadino = citt;
    }

    public static Cittadino getCittadino() {
        return cittadino;
    }
}