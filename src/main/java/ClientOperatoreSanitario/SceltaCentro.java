package ClientOperatoreSanitario;

import ServerPackage.CentroVaccinale;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SceltaCentro extends OperatoreRegVacc implements Initializable {

    //elementi grafici della scena e variabili private
    @FXML private TextField TFNome;
    @FXML private TextField TFComune;
    @FXML private ComboBox<String> CBTipologia;
    @FXML private TableView<CentroVaccinale> TableVRisultati;
    @FXML private TableColumn<CentroVaccinale, Integer> TCId;
    @FXML private TableColumn<CentroVaccinale, String> TCNome;
    @FXML private TableColumn<CentroVaccinale, String> TCInd;
    @FXML private TableColumn<CentroVaccinale, String> TCCivico;
    @FXML private TableColumn<CentroVaccinale, String> TCComune;
    @FXML private TableColumn<CentroVaccinale, String> TCSigla;
    @FXML private TableColumn<CentroVaccinale, Integer> TCCap;
    @FXML private TableColumn<CentroVaccinale, String> TCTipo;
    @FXML private AnchorPane APPane;

    private boolean cercaNome = true;
    private ArrayList<CentroVaccinale> arrayListRisultati = new ArrayList<>();

    //funzione che inizializza gli elementi grafici
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //inizializzazione ComboBox presenti nella pagina di registrazione del vaccinato
        CBTipologia.getItems().addAll("Aziendale", "Ospedaliero", "Hub");
        CBTipologia.setVisibleRowCount(3);
        CBTipologia.setEditable(false);

        TCId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TCNome.setCellValueFactory(new PropertyValueFactory<>("nomeCentro"));
        TCInd.setCellValueFactory(new PropertyValueFactory<>("nomeInd"));
        TCCivico.setCellValueFactory(new PropertyValueFactory<>("civico"));
        TCComune.setCellValueFactory(new PropertyValueFactory<>("comune"));
        TCSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        TCCap.setCellValueFactory(new PropertyValueFactory<>("cap"));
        TCTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    //permette di tornare indietro alla pagina di registrazione del vaccinato
    public void annulla(ActionEvent actionEvent) {
        //prendo lo stage della finestra per poterla chiudere quando si sceglie il centro
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }

    //conferma il centro scelto, chiude la pagina e torna indietro
    public void conferma(ActionEvent actionEvent) {
        //prendo lo stage della finestra per poterla chiudere quando si sceglie il centro
        CentroVaccinale centroVaccinale = TableVRisultati.getSelectionModel().getSelectedItem();
        staticLabel.setText(centroVaccinale.toString());
        centroRV = centroVaccinale;
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }

    //cerca il centro in base ai dati forniti
    public void cercaCentro(ActionEvent actionEvent) {

        String comune;
        String tipologia;
        String nomeCentro;

        OperatoreSanitarioAPP operatoreSanitarioAPP = new OperatoreSanitarioAPP();

        if (TFNome.isDisabled()) {
            comune = TFComune.getText().trim();
            tipologia = CBTipologia.getValue();

            arrayListRisultati = operatoreSanitarioAPP.cercaCentro(comune, tipologia);
            TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
        } else {
            nomeCentro = TFNome.getText().trim();

            arrayListRisultati = operatoreSanitarioAPP.cercaCentro(nomeCentro);
            TableVRisultati.setItems(FXCollections.observableArrayList(arrayListRisultati));
        }

    }

    //funzione che abilita e disabilita i campi in base a ciò che si vuole cercare
    public void oppure(ActionEvent actionEvent) {
        if (cercaNome) {
            TFNome.setDisable(true);
            TFComune.setDisable(false);
            CBTipologia.setDisable(false);
            cercaNome = false;
        }
        else {
            TFNome.setDisable(false);
            TFComune.setDisable(true);
            CBTipologia.setDisable(true);
            cercaNome = true;
        }
    }
}