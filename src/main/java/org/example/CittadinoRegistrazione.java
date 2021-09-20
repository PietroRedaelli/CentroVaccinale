package org.example;

import ClientCittadino.Cittadino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CittadinoRegistrazione implements Initializable {

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField codicefiscale;
    @FXML private TextField email;
    @FXML private TextField userid;
    @FXML private PasswordField password;
    @FXML private Button annullaid;
    @FXML private Button registratiid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void annulla() throws IOException {
        AppOperatore.setRoot("cittadinoMainMenu");
    }
    public void registrati(){
        if(controlloCampi()){
            String nomeUtente = nome.getText();
            String cognomeUtente = cognome.getText();
            String codicefiscaleUtente = codicefiscale.getText();
            String emailUtente = email.getText();
            String useridUtente = userid.getText();
            String passwordUtente = password.getText();
            Cittadino cittadino = new Cittadino(nomeUtente, cognomeUtente, codicefiscaleUtente, emailUtente, useridUtente, passwordUtente);
            System.out.println(cittadino);
        }
    }

    private boolean controlloCampi() {

        boolean controllo = true;

        if (nome.getText().equals("")) {
            //TFNomeCentro.setStyle("-fx-text-inner-color: #E93737;");      sbagliato perché poi anche le tue scritte diventano rosse
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
        return controllo;
    }
}
