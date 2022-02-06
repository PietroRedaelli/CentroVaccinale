package clientCVOperatoreSanitario;

import grafics.ConfirmBoxVacc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * La classe OperatoreRegVacc permette di gestire la registrazione di un nuovo vaccinato.
 * @author Pietro
 * @version 1.0
 */
public class OperatoreRegVacc implements Initializable {

    //Elementi grafici della finestra di salvataggio
    @FXML private TextField TFCentro;
    @FXML private TextField TFNome;
    @FXML private TextField TFCognome;
    @FXML private TextField TFFisc;
    @FXML private DatePicker DPData;
    @FXML private ComboBox<String> CBVacc;
    @FXML private ComboBox<String> CBDose;
    @FXML private TextField TFID;

    protected static TextField staticLabel;
    protected static CentroVaccinale centroRV;


    /**
     * Il metodo inizializza gli elementi grafici.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //istruzione fondamentale per poter scrivere in TFCentro il centro seleaionato nella finestra SceltaCentro
        staticLabel = TFCentro;

        //inizializzazione ComboBox presenti nella pagina di registrazione del vaccinato
        CBVacc.getItems().addAll("Pfizer", "AstraZeneca", "Moderna", "J&J");
        CBVacc.setVisibleRowCount(4);
        CBVacc.setEditable(false);
        CBVacc.setPromptText("--seleziona--");

        CBDose.getItems().addAll("1", "2", "3");
        CBDose.setVisibleRowCount(3);
        CBDose.setEditable(false);
        CBDose.setPromptText("/");

        //istruzione che setta la data odierna nel DataPicker
        DPData.setValue(LocalDate.now());

        //listener che fa diventare ciò che si scrive maiuscolo
        TFFisc.textProperty().addListener((observable, oldValue, newValue) -> TFFisc.setText(newValue.toUpperCase()));

        //listener per la lunghezza dei dati inseriti
        TFNome.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 20 ? change : null));
        TFCognome.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 30 ? change : null));
        TFFisc.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 16 ? change : null));

        //listener che permette di inserire solamente numeri all'interno del textfield associato all'ID
        TFID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                TFID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Serve per limitare ad un massimo 16 caratteri l'ID
        TFID.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 16 ? change : null));

        //generazione id vaccinazione
        TFID.setText(String.valueOf(generaIDVaccinazione()));
    }

    /**
     * Il metodo genera ID univoci di una vaccinazione.
     */
    private long generaIDVaccinazione() {
        return new Date().getTime() * 1000L + ThreadLocalRandom.current().nextLong(999L);
    }

    /**
     * Il metodo, tramite bottone, permette di tornare indietro alla pagina di scelta.
     */
    public void annulla() throws IOException {
        AppOperatoreSanitario.setRoot("operatoreSceltaReg.fxml");
    }

    /**
     * Il metodo permette di aprire una nuova finestra per la selezione del centro vaccinale.
     */
    public void selezionaCentro(ActionEvent actionEvent) throws IOException {
        //apro una nuova finestra
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("operatoreSceltaCentro.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Selezione Centro");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Il metodo permette di acquisire i campi inseriti relativi alla registrazione di un
     * nuovo vaccinato e che verranno successivamente inviati al database e salvati.
     */
    public void conferma() {

        if (controlloCampi()) {
            String nome = TFNome.getText().trim();
            String cognome = TFCognome.getText().trim();
            String codFisc = TFFisc.getText().trim();
            String data = DPData.getValue().toString();
            String vaccino = CBVacc.getValue();
            int dose = Integer.parseInt(CBDose.getValue());
            long id = Long.parseLong(TFID.getText());

            Vaccinato vaccinato = new Vaccinato( nome, cognome, centroRV.getID(), id, codFisc, data, vaccino, dose);

            boolean conferma = ConfirmBoxVacc.start(vaccinato, staticLabel.getText());

            if (conferma) {
                azzeraCampi();
            }
        }
    }

    /**
     * Il metodo ha lo scopo di azzerare i campi dopo aver registrato correttamente un nuovo vaccinato.
     */
    private void azzeraCampi() {
        TFNome.clear();
        TFCognome.clear();
        TFFisc.clear();
        CBVacc.valueProperty().set(null);
        CBDose.valueProperty().set(null);
        TFID.setText(String.valueOf(generaIDVaccinazione()));
        TFNome.setPromptText("");
        TFCognome.setPromptText("");
        TFFisc.setPromptText("");
        CBVacc.setPromptText("");
        CBDose.setPromptText("");
    }

    /**
     * Il metodo permette di controllare tutti i campi al fine di salvare correttamente
     * i dati del vaccinato che si vuole inserire.
     */
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

        if (TFCentro.getText().equals("")) {
            TFCentro.setStyle("-fx-prompt-text-fill: red;");
            TFCentro.setPromptText("Campo mancante!");
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
            CBVacc.setPromptText("SELEZIONA");
            controllo = false;
        }

        if (CBDose.getValue() == null) {
            CBDose.setPromptText("!");
            controllo = false;
        }

        return controllo;
    }
}