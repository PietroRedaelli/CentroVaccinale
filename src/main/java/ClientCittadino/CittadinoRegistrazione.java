package ClientCittadino;

import Grafics.ConfirmBoxCittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * La classe ha lo scopo di creare l'interfaccia grafica relativa alla
 * registrazione di un cittadino.
 * @author Pietro
 * @version 1.0
 */

public class CittadinoRegistrazione implements Initializable {

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField codicefiscale;
    @FXML private TextField email;
    @FXML private TextField userid;
    @FXML private PasswordField password;
    @FXML private TextField idVacc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //listener per la lunghezza dei dati inseriti
        nome.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 20 ? change : null));
        cognome.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 30 ? change : null));
        codicefiscale.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 16 ? change : null));
        email.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 40 ? change : null));
        userid.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 30 ? change : null));
        password.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 18 ? change : null));
        idVacc.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 16 ? change : null));
        //listener che fa diventare ciò che si scrive maiuscolo per Codice Fiscale
        codicefiscale.textProperty().addListener((observable, oldValue, newValue) -> codicefiscale.setText(newValue.toUpperCase()));
        //listener che permette di scrivere solo numeri per l'ID Vaccinazione
        idVacc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idVacc.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Il metodo annulla la registrazione e torna indietro.
     * @throws IOException annulla la registrazione e torna indietro al menu iniziale.
     */
    public void annulla() throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }

    /**
     * Il metodo permette la registrazione di un cittadino inserendo tutti i campi necessari.
     * @throws IOException registra un cittadino.
     */
    public void registrati() throws IOException {
        //controllo dei campi della finestra
        if(controlloCampi()){
            String nomeUtente = nome.getText();
            String cognomeUtente = cognome.getText();
            String codicefiscaleUtente = codicefiscale.getText();
            String emailUtente = email.getText();
            String useridUtente = userid.getText();
            String passwordUtente = password.getText();
            long idVaccinazione = Long.parseLong(idVacc.getText());
            Cittadino cittadino = new Cittadino(nomeUtente, cognomeUtente, codicefiscaleUtente, emailUtente, useridUtente, passwordUtente, idVaccinazione);
            boolean conferma = ConfirmBoxCittadino.start(cittadino);
            if (conferma){
                azzeraCampi();
                CittadinoRegEvento.setCittadino(cittadino);
                AppCittadino.setRoot("cittadinoRegEvento.fxml");
            }
        }
    }

    /**
     * Il metodo controlla che i campi inseriti in fase di registrazione siano corretti.
     * @return true se tutti i campi inseriti sono corretti, false altrimenti.
     */
    private boolean controlloCampi() {

        boolean controllo = true;

        if (nome.getText().equals("")) {
            nome.setStyle("-fx-prompt-text-fill: red;");
            nome.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (cognome.getText().equals("")) {
            cognome.setStyle("-fx-prompt-text-fill: red;");
            cognome.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (codicefiscale.getText().length() < 16) {
            codicefiscale.clear();
            codicefiscale.setStyle("-fx-prompt-text-fill: red;");
            codicefiscale.setPromptText("Lunghezza minima 16 caratteri!");
            controllo = false;
        }

        if (email.getText().equals("")) {
            email.setStyle("-fx-prompt-text-fill: red;");
            email.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (userid.getText().length() <4) {
            userid.clear();
            userid.setStyle("-fx-prompt-text-fill: red;");
            userid.setPromptText("Lunghezza min 4 caratteri e max 30 caratteri!");
            controllo = false;
        }

        if (password.getText().length() < 4) {
            password.clear();
            password.setStyle("-fx-prompt-text-fill: red;");
            password.setPromptText("Lunghezza min 4 caratteri e max 18 caratteri!");
            controllo = false;
        }

        if (idVacc.getText().length() < 16) {
            idVacc.clear();
            idVacc.setStyle("-fx-prompt-text-fill: red;");
            idVacc.setPromptText("Inserisci un ID valido di 16 caratteri!");
            controllo = false;
        }
        return controllo;
    }

    /**
     * Il metodo azzera i campi dopo aver registrato correttamente un cittadino.
     */
    public void azzeraCampi() {
        nome.clear();
        cognome.clear();
        codicefiscale.clear();
        email.clear();
        userid.clear();
        password.clear();
        idVacc.clear();
        nome.setPromptText("");
        cognome.setPromptText("");
        codicefiscale.setPromptText("");
        email.setPromptText("");
        userid.setPromptText("");
        password.setPromptText("");
        idVacc.setPromptText("");
    }
}