package ClientOperatoreSanitario;

import ClientCittadino.AppCittadino;
import ServerPackage.ServerInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class OperatoreSceltaCentro extends OperatoreRegVacc implements Initializable {

    //elementi grafici della scena e variabili private
    @FXML private TextField TFNome;
    @FXML private TextField TFComune;
    @FXML private ComboBox<String> CBTipologia;
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
    private final OperatoreSanitarioAPP OS = new OperatoreSanitarioAPP();
    private static final ServerInterface si = AppCittadino.si;
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

        if(si == null){
            arrayListRisultati = OS.cercaCentro(nomeCentro);
        }else{
            try {
                arrayListRisultati = si.cercaCentroVaccinale(nomeCentro);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
    }

    //permette di tornare indietro alla pagina di registrazione del vaccinato
    public void annulla(ActionEvent actionEvent) {
        //prendo lo stage della finestra per poterla chiudere quando si sceglie il centro
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }

    //conferma il centro scelto, chiude la pagina e torna indietro
    public void conferma(ActionEvent actionEvent) throws IOException {

        CentroVaccinale cv = TableVRisultati.getSelectionModel().getSelectedItem();
        staticLabel.setText(cv.getNomeCentro() + " (" + cv.getTipo() + "), " + cv.getIndirizzoCentro() + " " + cv.getCivico() + ", " + cv.getComune() + " " + cv.getSigla() + " " + cv.getCap());
        centroRV = cv;
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();

    }

    //Bottone: cerca il centro in base ai dati forniti (o per nome oppure per comune e tipologia)
    public void cercaCentro(ActionEvent actionEvent) {
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

    }

    //funzione che abilita e disabilita i campi in base a ciò che si vuole cercare
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