package clientCVOperatoreSanitario;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * La classe OperatoreSceltaCentro ha lo scopo di permettere ad un operatore sanitario
 * di scegliere e selezionare un centro vaccinale al fine di registrare una vaccinazione.
 * @author Pietro
 * @version 1.0
 */
public class OperatoreSceltaCentro extends OperatoreRegVacc implements Initializable {

    //elementi grafici della scena e variabili private
    @FXML private TextField TFNome;
    @FXML private TextField TFComune;
    @FXML private ComboBox<String> CBTipologia;
    @FXML private Label LBConnessione;
    @FXML private TableView<CentroVaccinale> TableVRisultati;
    @FXML private TableColumn<CentroVaccinale, String> TCNome;
    @FXML private TableColumn<CentroVaccinale, String> TCInd;
    @FXML private TableColumn<CentroVaccinale, String> TCCivico;
    @FXML private TableColumn<CentroVaccinale, String> TCComune;
    @FXML private TableColumn<CentroVaccinale, String> TCSigla;
    @FXML private TableColumn<CentroVaccinale, Integer> TCCap;
    @FXML private TableColumn<CentroVaccinale, String> TCTipo;
    @FXML private AnchorPane APPane;

    //variabili finestra
    private boolean checkNome = true;
    private ArrayList<CentroVaccinale> arrayListRisultati = new ArrayList<>();
    private final AppOperatoreSanitario OS = new AppOperatoreSanitario();
    private String nomeCentro = "";

    //funzione che inizializza gli elementi grafici
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //inizializzazione ComboBox presenti nella pagina di registrazione del vaccinato
        CBTipologia.getItems().addAll("Aziendale", "Ospedaliero", "Hub");
        CBTipologia.setVisibleRowCount(3);
        CBTipologia.setEditable(false);

        //settaggio tabella dei Centri
        TCNome.setCellValueFactory(new PropertyValueFactory<>("nomeCentro"));
        TCInd.setCellValueFactory(new PropertyValueFactory<>("indirizzoCentro"));
        TCCivico.setCellValueFactory(new PropertyValueFactory<>("civico"));
        TCComune.setCellValueFactory(new PropertyValueFactory<>("comune"));
        TCSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        TCCap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        TCTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        //prima ricerca generale con tutti i centri
        try {
            arrayListRisultati = OS.cercaCentro(nomeCentro);
            TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
        } catch (RemoteException e) {
            //e.printStackTrace();
            System.err.println("Nessuna connessione!");
        }
    }

    /**
     * Il metodo permette, tramite bottone, di tornare indietro alla pagina di registrazione del vaccinato.
     */
    public void annulla(ActionEvent actionEvent) {
        //prendo lo stage della finestra per poterla chiudere quando si sceglie il centro
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Il metodo permette, tramite bottone, di confermare il centro scelto.
     * la pagina verrà chiusa e si torna indietro.
     */

    public void conferma(ActionEvent actionEvent) throws IOException {

        CentroVaccinale cv = TableVRisultati.getSelectionModel().getSelectedItem();
        staticLabel.setText(cv.getNomeCentro() + " (" + cv.getTipo() + "), " + cv.getIndirizzoCentro() + " " + cv.getCivico() + ", " + cv.getComune() + " " + cv.getSigla() + " " + cv.getCap());
        centroRV = cv;
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Il metodo permette, tramite bottone, cercare il centro in base ai dati forniti
     * (per nome, oppure per comune e tipologia)
     */
    public void cercaCentro(ActionEvent actionEvent) {
        try {
            if (TFNome.isDisabled()) {
                String comune = TFComune.getText().trim();
                String tipologia = CBTipologia.getValue();

                arrayListRisultati = OS.cercaCentro(comune, tipologia);                 //RICORDATI DI AGGIUNGERE IL CASO IN CUI SI APRE LA FINESTRA PER LE INFO
                TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
            } else {
                nomeCentro = TFNome.getText().trim();

                arrayListRisultati = OS.cercaCentro(nomeCentro);
                TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
            }
        } catch (RemoteException e) {
            //qunado il server si disconnette compare il label dell'errore
            LBConnessione.setVisible(true);
        }
    }

    /**
     * Il metodo permette, tramite bottone, di abilitare e disabilitare i campi ai fini della ricerca.
     */
    public void oppure(ActionEvent actionEvent) {
        if (checkNome) {
            TFNome.setDisable(true);
            TFComune.setDisable(false);
            CBTipologia.setDisable(false);
            checkNome = false;
        }
        else {
            TFNome.setDisable(false);
            TFComune.setDisable(true);
            CBTipologia.setDisable(true);
            checkNome = true;
        }
    }
}