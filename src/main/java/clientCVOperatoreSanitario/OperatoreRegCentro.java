package clientCVOperatoreSanitario;

import grafics.ConfirmBoxCentro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * La classe OperatoreRegCentro permette la gestione della registrazione di un nuovo centro vaccinale.
 * @author Pietro
 * @version 1.0
 */

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

    /**
     * Il metodo inizializza gli elementi grafici del relativo fxml.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //inizializzazione ComboBox presenti nella pagina di registrazione del centro
        CBQualific.getItems().addAll("Via", "Viale", "Piazza");
        CBQualific.setVisibleRowCount(3);
        CBQualific.setEditable(false);
        CBQualific.setPromptText("--seleziona--");

        CBTipo.getItems().addAll("Aziendale", "Ospedaliero", "Hub");
        CBTipo.setVisibleRowCount(3);
        CBTipo.setEditable(false);
        CBTipo.setPromptText("--seleziona--");


        //listener che permette di inserire solamente numeri all'interno del textfield associato al CAP
        TFCap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                TFCap.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Serve per limitare ad un massimo 5 caratteri il CAP
        TFCap.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 5 ? change : null));

        //listener che fa diventare ciò che si scrive maiuscolo
        TFSigla.textProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue.matches("[A-Za-z]*"))
                TFSigla.setText(newValue.toUpperCase());
        });
        //Serve per limitare ad un massimo 2 caratteri la sigla
        TFSigla.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 2 ? change : null));
        //Serve per non inserire numeri nella sigla della città
        TFSigla.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[A-Za-z]*")) {
                TFSigla.setText(oldValue);
            }
        });

        //Serve per limitare ad un massimo 4 caratteri il numero civico
        TFNCivico.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 4 ? change : null));
    }

    /**
     * Il metodo, tramite bottone, permette di tornare indietro alla pagina di scelta.
     */
    public void indietro() throws IOException {
        AppOperatoreSanitario.setRoot("operatoreSceltaReg.fxml");
    }

    /**
     * Il metodo permette di acquisire i campi relativi alla registrazione di un nuovo centro
     * vaccinale che verranno successivamente inviati al database e salvati.
     */
    public void conferma() throws IOException {

        if (controlloCampi()) {

            String nomeCentro = TFNomeCentro.getText().trim();
            String comune = TFComune.getText().trim();
            String qualificatore = CBQualific.getValue();
            String nomeInd = TFNomeInd.getText().trim();
            String nCivico = TFNCivico.getText().trim();
            String sigla = TFSigla.getText().trim();
            int cap = Integer.parseInt(TFCap.getText().trim());
            String tipologia = CBTipo.getValue();

            CentroVaccinale centro = new CentroVaccinale(nomeCentro, comune, qualificatore + " " + nomeInd, nCivico, sigla, cap, tipologia);

            boolean conferma = ConfirmBoxCentro.start(centro);

            if (conferma) {
                azzeraCampi();
            }
        }
    }

    /**
     * Il metodo azzera i campi dopo aver registrato correttamente un centro vaccinale.
     */
    public void azzeraCampi() {
        TFNomeCentro.clear();
        TFNomeInd.clear();
        TFNCivico.clear();
        TFComune.clear();
        TFSigla.clear();
        TFCap.clear();
        CBQualific.valueProperty().set(null);
        CBTipo.valueProperty().set(null);
        TFNomeCentro.setPromptText("");
        TFNomeInd.setPromptText("");
        TFNCivico.setPromptText("");
        TFComune.setPromptText("");
        TFSigla.setPromptText("");
        TFCap.setPromptText("");
        CBQualific.setPromptText("");
        CBTipo.setPromptText("");
    }

    /**
     * Il metodo controlla i campi prima di salvare correttamente i dati del centro vaccinale.
     */
    private boolean controlloCampi() {

        boolean controllo = true;

        if (TFNomeCentro.getText().equals("")) {
            TFNomeCentro.setStyle("-fx-prompt-text-fill: red;");
            TFNomeCentro.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFNomeInd.getText().equals("")) {
            TFNomeInd.setStyle("-fx-prompt-text-fill: red;");
            TFNomeInd.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFNCivico.getText().equals("")) {
            TFNCivico.setStyle("-fx-prompt-text-fill: red;");
            TFNCivico.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFComune.getText().equals("")) {
            TFComune.setStyle("-fx-prompt-text-fill: red;");
            TFComune.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFSigla.getText().equals("")) {
            TFSigla.setStyle("-fx-prompt-text-fill: red;");
            TFSigla.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (TFCap.getText().equals("")) {
            TFCap.setStyle("-fx-prompt-text-fill: red;");
            TFCap.setPromptText("Campo mancante!");
            controllo = false;
        }

        if (CBQualific.getValue() == null) {
            CBQualific.setPromptText("SELEZIONA");
            controllo = false;
        }

        if (CBTipo.getValue() == null) {
            CBTipo.setPromptText("SELEZIONA");
            controllo = false;
        }

        return controllo;
    }
}