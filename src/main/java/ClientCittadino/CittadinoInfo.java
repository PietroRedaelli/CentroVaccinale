package ClientCittadino;

import ServerPackage.ServerInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CittadinoInfo extends CittadinoSceltaCentro implements Initializable {

    //elementi grafici
    @FXML private AnchorPane APPane;
    @FXML private TableView<EventoAvverso> TableVRecap;
    @FXML private TableColumn<EventoAvverso, String> TCEvento;
    @FXML private TableColumn<EventoAvverso, Integer> TCNum;
    @FXML private TableColumn<EventoAvverso, Integer> TCAvg;
    @FXML private Label LBNome;
    @FXML private Label LBIndirizzo;
    @FXML private Label LBTipo;

    private static final ServerInterface si = AppCittadino.si;
    private ArrayList<EventoAvverso> arrayListRisultati = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //inizializzazione delle colonne
        TCEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        TCNum.setCellValueFactory(new PropertyValueFactory<>("severita"));
        TCAvg.setCellValueFactory(new PropertyValueFactory<>("intMedia"));

        //inizializzazione dei label con le informazioni del centro
        LBNome.setText(centroVaccinaleInfo.getNomeCentro());
        LBIndirizzo.setText(centroVaccinaleInfo.getIndirizzoCentro() + " " + centroVaccinaleInfo.getCivico() + ", " +
                centroVaccinaleInfo.getComune() + " " + centroVaccinaleInfo.getSigla() + " " +
                centroVaccinaleInfo.getCap());
        LBTipo.setText(centroVaccinaleInfo.getTipo());

        //raccolta dei vari dati per il prospetto del centro
        try {
            arrayListRisultati = si.visualizzaInfoCentroVaccinale(centroVaccinaleInfo.getID());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        TableVRecap.setItems(FXCollections.observableArrayList(arrayListRisultati));
    }

    //chiude la pagina aperta e torna alla scelta del centro
    public void indietro(ActionEvent actionEvent) {
        Stage stage = (Stage) APPane.getScene().getWindow();
        stage.close();
    }
}