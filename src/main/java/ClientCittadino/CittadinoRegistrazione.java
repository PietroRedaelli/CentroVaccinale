package ClientCittadino;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//PIETRO: registrazione nel DB

public class CittadinoRegistrazione implements Initializable {

    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField codicefiscale;
    @FXML private TextField email;
    @FXML private TextField userid;
    @FXML private PasswordField password;
    @FXML private Button annullaid;
    @FXML private Button registratiid;

    public static ArrayList<Cittadino> registrati = new ArrayList<>();// da eliminare

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void annulla() throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }
    public void registrati() throws IOException {
        if(controlloCampi()){
            String nomeUtente = nome.getText();
            String cognomeUtente = cognome.getText();
            String codicefiscaleUtente = codicefiscale.getText();
            String emailUtente = email.getText();
            String useridUtente = userid.getText();
            String passwordUtente = password.getText();
            Cittadino cittadino = new Cittadino(nomeUtente, cognomeUtente, codicefiscaleUtente, emailUtente, useridUtente, passwordUtente);
            //controllo se esiste gia un registrato con questi dati...

            //fai partire la finestra di recap dei dati per far confermare al cittadino

            //aggiunta del registrato nella tabella Vaccinati

            registrati.add(cittadino);//da eliminare

            AppCittadino.setRoot("cittadinoRegEvento.fxml");
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
