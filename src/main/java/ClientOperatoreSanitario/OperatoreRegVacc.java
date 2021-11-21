package ClientOperatoreSanitario;

import Grafics.ConfirmBoxVacc;
import ServerPackage.CentroVaccinale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

//Classe che gestisce la registrazione di un nuovo vaccinato

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


    //funzione che inizializza gli elementi grafici
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
        TFFisc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                TFFisc.setText(newValue.toUpperCase());
            }
        });

        //Serve per limitare ad un massimo 16 caratteri il codice fiscale
        TFFisc.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 16 ? change : null));

        //listener che permette di inserire solamente numeri all'interno del textfield associato all'ID
        TFID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    TFID.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Serve per limitare ad un massimo 16 caratteri l'ID
        TFID.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 16 ? change : null));

        //generazione id vaccinazione
        TFID.setText(String.valueOf(generaIDVaccinazione()));
    }

    //funzinoe che genera id univoci
    private long generaIDVaccinazione() {
        return new Date().getTime() * 1000L + ThreadLocalRandom.current().nextLong(999L);
    }

    //funzione che permette di tornare indietro alla pagina di scelta
    public void annulla() throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreSceltaReg.fxml");
    }

    //funzione che apre una nuova finestra per la selezione del centro
    public void selezionaCentro(ActionEvent actionEvent) throws IOException {
        //apro una nuova finestra
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sceltaCentro.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Selezione Centro");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    //funzione che permette di acquisire i campi relativi alla registrazione di un nuovo vaccinato che verranno poi inviati al database e salvati
    public void conferma() {

        if (controlloCampi()) {
            String nome = TFNome.getText().trim();
            String cognome = TFCognome.getText().trim();
            String codFisc = TFFisc.getText().trim();
            String data = DPData.getValue().toString();
            String vaccino = CBVacc.getValue();
            int dose = Integer.parseInt(CBDose.getValue());
            long id = Long.parseLong(TFID.getText());

            Vaccinato vaccinato = new Vaccinato(0, nome, cognome, centroRV.getID(), id, codFisc, data, vaccino, dose);

            boolean conferma = ConfirmBoxVacc.start(vaccinato, centroRV.toString());

            if (conferma) {
                azzeraCampi();
            }
        }
    }

    //funzione che azzera i campi dopo aver registrato correttamente un vaccinato
    private void azzeraCampi() {
        TFNome.clear();
        TFCognome.clear();
        TFFisc.clear();
        CBVacc.valueProperty().set(null);
        CBDose.valueProperty().set(null);
        TFID.setText(String.valueOf(generaIDVaccinazione()));
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