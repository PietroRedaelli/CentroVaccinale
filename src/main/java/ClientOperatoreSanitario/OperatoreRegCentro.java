package org.example;

import ServerPackage.CentroVaccinale;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//Classe che gestisce la registrazione di un nuovo cenro vaccinale

public class OperatoreRegCentro implements Initializable {


    //Elementi grafici della finestra di salvataggio
    @FXML private ComboBox<String> CBQualific;
    @FXML private ComboBox<String> CBTipo;
    @FXML private TextField TFNomeCentro;
    @FXML private TextField TFNomeInd;
    @FXML private TextField TFNCivico;
    @FXML private TextField TFComune;
    @FXML private TextField TFSigla;
    @FXML private TextField TFCap;


    //funzione che inizializza i ComboBox presenti nella pagina di registrazione inserendo tutte le opzioni possibili di scelta
    @Override
    public void initialize(URL url, ResourceBundle rb){

        CBQualific.getItems().addAll("Via", "Viale", "Piazza");
        CBQualific.setVisibleRowCount(3);
        CBQualific.setEditable(false);
        CBQualific.setPromptText("--seleziona--");

        CBTipo.getItems().addAll("Aziendale", "Ospedaliero", "Hub");
        CBTipo.setVisibleRowCount(3);
        CBTipo.setEditable(false);
        CBTipo.setPromptText("--seleziona--");

    }

    //funzione che permette di tornare indietro alla pagina di scelta
    public void annulla() throws IOException {
        App.setRoot("operatoreSceltaReg");
    }

    //funzione che permette di acquisire i campi relativi alla registrazione di un nuovo centro che verranno poi inviati al database e salvati
    public void conferma(){

        if (controlloCampi()) {

            String nomeCentro = TFNomeCentro.getText();
            String comune = TFComune.getText();
            String qualificatore = CBQualific.getValue();
            String nomeInd = TFNomeInd.getText();
            String nCivico = TFNCivico.getText();
            String sigla = TFSigla.getText();
            String cap = TFCap.getText();
            String tipologia = CBTipo.getValue();


            CentroVaccinale centro = new CentroVaccinale(nomeCentro, comune, qualificatore, nomeInd, nCivico, sigla, cap, tipologia);
            System.out.println(centro);
        }
    }

    //funzione che permette di controllare tutti i campi per poter salvare correttamente i dati del centro che si vuole inserire
    private boolean controlloCampi() {

        boolean controllo = true;

        if (TFNomeCentro.getText().equals("")) {
            //TFNomeCentro.setStyle("-fx-text-inner-color: #E93737;");      sbagliato perché poi anche le tue scritte diventano rosse
            TFNomeCentro.setStyle("-fx-prompt-text-fill: red;");
            TFNomeCentro.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFNomeInd.getText().equals("")) {
            TFNomeInd.setStyle("-fx-prompt-text-fill: red;");
            TFNomeInd.setPromptText("Campo mancante!");
            controllo = false;        }

        if (TFNCivico.getText().equals("")) {
            TFNCivico.setStyle("-fx-prompt-text-fill: red;");
            TFNCivico.setPromptText("Campo mancante!");
            controllo = false;        }

        if (TFComune.getText().equals("")) {
            TFComune.setStyle("-fx-prompt-text-fill: red;");
            TFComune.setPromptText("Campo mancante!");
            controllo = false;        }

        if (TFSigla.getText().equals("")) {
            TFSigla.setStyle("-fx-prompt-text-fill: red;");
            TFSigla.setPromptText("Campo mancante!");
            controllo = false;        }

        if (TFNomeInd.getText().equals("")) {
            TFCap.setStyle("-fx-prompt-text-fill: red;");
            TFCap.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (CBQualific.getValue() == null) {
            //CBQualific.setStyle("-fx-text-fill: derive(-fx-control-inner-background,-80%)");  ne ho provati tanti ma non funzionano
            CBQualific.setPromptText("SELEZIONA");
            controllo = false;
        }

        if (CBTipo.getValue() == null) {
            //CBTipo.setStyle("-fx-prompt-text-fill: red;");
            CBTipo.setPromptText("SELEZIONA");
            controllo = false;
        }

        return controllo;
    }

}
