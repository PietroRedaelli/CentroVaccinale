package ClientCittadino;

import Grafics.ConfirmBoxCentro;
import Grafics.ConfirmBoxCittadino;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//PIETRO: registrazione nel DB

public class CittadinoRegistrazione implements Initializable {

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField codicefiscale;
    @FXML private TextField email;
    @FXML private TextField userid;
    @FXML private PasswordField password;
    @FXML private TextField idVacc;
    @FXML private Button annullaid;
    @FXML private Button registratiid;
    @FXML private TextField IDVaccField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //listener che fa diventare ciò che si scrive maiuscolo per Codice Fiscale
        codicefiscale.textProperty().addListener((observable, oldValue, newValue) -> codicefiscale.setText(newValue.toUpperCase()));
        //Serve per limitare a un massimo 16 caratteri per il Codice Fiscale
        codicefiscale.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 16 ? change : null));
        //listener che permette di scrivere solo numeri per l'ID Vaccinazione
        idVacc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idVacc.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //Serve per limitare a un massimo di 16 caratteri per l'ID Vaccinazione
        idVacc.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 16 ? change : null));


    }
    public void annulla() throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }
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
                CittadinoRegEvento.cittadino = cittadino;
                AppCittadino.setRoot("cittadinoRegEvento.fxml");
            }
        }


    }

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

        if (codicefiscale.getText().equals("")) {
            codicefiscale.setStyle("-fx-prompt-text-fill: red;");
            codicefiscale.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (email.getText().equals("")) {
            email.setStyle("-fx-prompt-text-fill: red;");
            email.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (userid.getText().equals("")) {
            userid.setStyle("-fx-prompt-text-fill: red;");
            userid.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (password.getText().equals("")) {
            password.setStyle("-fx-prompt-text-fill: red;");
            password.setPromptText("Campo mancante!");
            controllo = false;
        }
        if (idVacc.getText().equals("")) {
            idVacc.setStyle("-fx-prompt-text-fill: red;");
            idVacc.setPromptText("Campo mancante!");
            controllo = false;
        }
        return controllo;
    }

    //funzione che azzera i campi dopo aver registrato correttamente un cittadino
    public void azzeraCampi() {
        nome.clear();
        cognome.clear();
        codicefiscale.clear();
        email.clear();
        userid.clear();
        password.clear();
        idVacc.clear();
    }
}
