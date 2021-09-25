package ClientOperatoreSanitario;

import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import ClientOperatoreSanitario.Vaccinato;
import Grafics.ConfirmBoxCentro;
import Grafics.ConfirmBoxVacc;
import ServerPackage.CentroVaccinale;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OperatoreRegVacc implements Initializable {

    //Elementi grafici della finestra di salvataggio
    @FXML
    private Spinner<String> SPCentro;
    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFCognome;
    @FXML
    private TextField TFFisc;
    @FXML
    private DatePicker DPData;
    @FXML
    private ComboBox<String> CBVacc;
    @FXML
    private ComboBox<String> CBDose;
    @FXML
    private TextField TFID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CBVacc.getItems().addAll("Pfizer", "AstraZeneca", "Moderna", "J&J");
        CBVacc.setVisibleRowCount(4);
        CBVacc.setEditable(false);
        CBVacc.setPromptText("--seleziona--");

        CBDose.getItems().addAll("1", "2", "3");
        CBDose.setVisibleRowCount(3);
        CBDose.setEditable(false);
        CBDose.setPromptText("/");
        
        //si può inserire direttamente la data odierna?
        DPData.setValue(LocalDate.now());

    }

    //funzione che permette di tornare indietro alla pagina di scelta
    public void annulla() throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreSceltaReg");
    }

    //funzione che permette di acquisire i campi relativi alla registrazione di un nuovo vaccinato che verranno poi inviati al database e salvati
    public void conferma() {

        if (controlloCampi()) {

            String nomeCentro = SPCentro.getValue();
            String nome = TFNome.getText().trim();
            String cognome = TFCognome.getText().trim();
            String codFisc = TFFisc.getText().trim();
            String data = DPData.getValue().toString();
            String vaccino = CBVacc.getValue();
            String dose = CBDose.getValue();
            String id = TFID.getText().trim();

            Vaccinato vaccinato = new Vaccinato(nome, cognome, nomeCentro, id, codFisc, data, vaccino, dose );

            boolean conferma = ConfirmBoxVacc.start(nomeCentro, nome + " " + cognome,  codFisc, data, vaccino + " " + dose, id);

            if (conferma) {
                azzeraCampi();
            }
        }
    }

    private void azzeraCampi() {

        SPCentro.cancelEdit();
        TFNome.clear();
        TFCognome.clear();
        TFFisc.clear();
        //DPData.getEditor().clear();
        CBVacc.valueProperty().set(null);
        CBDose.valueProperty().set(null);
        TFID.clear();
    }

    //funzione che permette di controllare tutti i campi per poter salvare correttamente i dati del vaccinato che si vuole inserire
    private boolean controlloCampi() {

        boolean controllo = true;


        if (TFNome.getText().equals("")) {
            TFNome.setStyle("-fx-prompt-text-fill: red;");
            TFNome.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFCognome.getText().equals("")) {
            TFCognome.setStyle("-fx-prompt-text-fill: red;");
            TFCognome.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (SPCentro.getValue() != null) {          //RICORDATI DI CAMBIARE IL !=
            //TFNomeCentro.setStyle("-fx-text-inner-color: #E93737;");      sbagliato perché poi anche le tue scritte diventano rosse
            SPCentro.setStyle("-fx-prompt-text-fill: red;");
            SPCentro.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFID.getText().equals("")) {
            TFID.setStyle("-fx-prompt-text-fill: red;");
            TFID.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFFisc.getText().equals("")) {
            TFFisc.setStyle("-fx-prompt-text-fill: red;");
            TFFisc.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (DPData.getValue() == null) {
            DPData.setStyle("-fx-prompt-text-fill: red;");
            DPData.setPromptText("Campo mancante!");
            controllo = false;
        }


        if (CBVacc.getValue() == null) {
            //CBQualific.setStyle("-fx-text-fill: derive(-fx-control-inner-background,-80%)");  ne ho provati tanti ma non funzionano
            CBVacc.setPromptText("SELEZIONA");
            controllo = false;
        }

        if (CBDose.getValue() == null) {
            //CBTipo.setStyle("-fx-prompt-text-fill: red;");
            CBDose.setPromptText("!");
            controllo = false;
        }

        return controllo;
    }

}
