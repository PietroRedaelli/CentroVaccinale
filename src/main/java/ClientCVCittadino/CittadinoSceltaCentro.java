package ClientCVCittadino;

import ClientCVOperatoreSanitario.CentroVaccinale;
import ServerCV.ServerInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * La classe permette di selezionare un centro vaccinale tra quelli disponibili.
 * @author Pietro
 * @version 1.0
 */
public class CittadinoSceltaCentro implements Initializable {

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

    //variabili finestra
    private boolean checkNome = true;
    private ArrayList<CentroVaccinale> arrayListRisultati = new ArrayList<>();
    private final AppCittadino cittadino = new AppCittadino();
    private static final ServerInterface si = AppCittadino.si;
    private String nomeCentro = "";
    protected static CentroVaccinale centroVaccinaleInfo;

    /**
     * Il metodo inizializza i parametri necessari alla visualizzazione grafica
     * della scelta di un centro vaccinale.
     */
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
            arrayListRisultati = si.cercaCentroVaccinale(nomeCentro);
            TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
        } catch (RemoteException e) {
            //e.printStackTrace();
            System.err.println("Nessuna connessione!");
        }
    }

    /**
     * Il metodo permette di tornare indietro alla pagina di introduzione dei cittadini.
     */
    public void annulla(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoMainMenu.fxml");
    }

    /**
     * Il metodo permette di confermare le informazioni del centro vaccinale selezionato.
     */
    public void conferma(ActionEvent actionEvent) throws IOException {

        centroVaccinaleInfo = TableVRisultati.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("cittadinoInfo.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Info");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    /**
     * Il metodo, tramite bottone, permette di cercare il centro vaccinale  in base ai dati immessi.
     */
    public void cercaCentro(ActionEvent actionEvent) {
        try {
            if (TFNome.isDisabled()) {
                String comune = TFComune.getText().trim();
                String tipologia = CBTipologia.getValue();

                arrayListRisultati = cittadino.cercaCentro(comune, tipologia);
                TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
            } else {
                nomeCentro = TFNome.getText().trim();

                arrayListRisultati = cittadino.cercaCentro(nomeCentro);
                TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
            }
        } catch(RemoteException e) {
            //qunado il server si disconnette compare il label dell'errore
            LBConnessione.setVisible(true);
        }
    }

    /**
     * Il metodo abilita e disabilita i campi in base a ciò che si vuole cercare.
     */
    public void oppure(ActionEvent actionEvent) {
        if (checkNome) {
            TFNome.setDisable(true);
            TFComune.setDisable(false);
            CBTipologia.setDisable(false);
            checkNome = false;
        } else {
            TFNome.setDisable(false);
            TFComune.setDisable(true);
            CBTipologia.setDisable(true);
            checkNome = true;
        }
    }
}