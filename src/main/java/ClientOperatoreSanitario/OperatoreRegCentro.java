package ClientOperatoreSanitario;

import Grafics.ConfirmBoxCentro;
import ServerPackage.CentroVaccinale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

        //listener che permette di inserire solamente numeri all'interno dela textfield associato al CAP
        TFCap.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    TFCap.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    //funzione che permette di tornare indietro alla pagina di scelta
    public void annulla() throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreSceltaReg.fxml");
    }

    //funzione che permette di acquisire i campi relativi alla registrazione di un nuovo centro che verranno poi inviati al database e salvati
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

            CentroVaccinale centro = new CentroVaccinale(nomeCentro, comune, qualificatore, nomeInd, nCivico, sigla, cap, tipologia);

            //con questa serie di operazioni si può aprire la nuova finestra
            /*
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("clienteRegEvento.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Conferma");
            stage.initModality(Modality.APPLICATION_MODAL);     //la finestra in backgroud non può essere cliccata fin quando la nuova finestra non viene chiusa
            stage.setResizable(false);
            stage.show();

            OperatorePostReg operatorePostReg = fxmlLoader.getController();
            operatorePostReg.inviaDati(nomeCentro, qualificatore + " " + nomeInd + ", " + nCivico + "\n" + cap + " " + comune + " " + sigla, tipologia);
            operatorePostReg.inviaCentro(centro);*/

            boolean conferma = ConfirmBoxCentro.start(nomeCentro, qualificatore + " " + nomeInd + ", " + nCivico + " " + cap + " " + comune + " " + sigla, tipologia);

            if (conferma) {
                azzeraCampi();
            }

        }
    }

    public void azzeraCampi() {

        TFNomeCentro.clear();
        TFNomeInd.clear();
        TFNCivico.clear();
        TFComune.clear();
        TFSigla.clear();
        TFCap.clear();
        CBQualific.valueProperty().set(null);
        CBTipo.valueProperty().set(null);
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

        if (TFCap.getText().equals("")) {
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
