package ClientOperatoreSanitario;

import Grafics.ConfirmBoxCentro;
import Grafics.ConfirmBoxVacc;
import ServerPackage.ServerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
/**
 * La classe OperatoreSanitarioAPP permette di creare e inizializzare
 * l'interfaccia grafica del ClientOperatoreSanitario.
 * @author Pietro
 * @version 1.0
 */
public class OperatoreSanitarioAPP extends Application {

    private static ServerInterface si;
    private static Stage stage1;

    /**
     * Il metodo inizializza la finestra grafica relativa al file fxml.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage1 = stage;
        Scene scene = new Scene(loadFXML("operatoreLoading.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("CentroVaccinale");
        stage1.show();
        stage1.setResizable(false);
    }

    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    /**
     * Il metodo permette di inizializzare la connessione al server.
     * @param args
     */
    public static void main(String[] args){
        connessione_server();
        launch();
    }

    public static boolean connessione_server(){
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            //System.err.println("Client Operatore Sanitario: errore di connessione al server \n" + e.getMessage());
            //System.exit(0);
            return false;
        }
        return true;
    }

    /**
     * Il metodo permette di inviare la richiesta di registrare un centro vaccinale al server.
     * @param centro
     */
    public static void registraCentroVaccinale(CentroVaccinale centro){
        System.out.println("Registrazione Centro Vaccinale");
        try {
            si.registraCentroVaccinale(centro);
            System.out.println(centro + "\nCompletata registrazione Centro Vaccinale");
        } catch (RemoteException e) {
            System.out.println("Error!!!\n" + e.getMessage());
        }
    }

    /**
     * Il metodo controlla i campi del vaccinato con i dati nel databae e se essi sono corretti
     * invia la richiesta di registrare un vaccinato al server.
     */
    public void registraVaccinato(Vaccinato vaccinato) {
        System.out.println("Registrazione Vaccinato");
        try {
            si.registraVaccinato(vaccinato);
            System.out.println(vaccinato+"\nCompletata registrazione Vaccinato");
        } catch (RemoteException e) {
            e.printStackTrace();
            ConfirmBoxCentro.setError("Errore di connessione con il Server");
        }
    }

    /**
     * Il metodo effettua una richiesta al server di ricerca del centro inserito in base al nome.
     */
    public ArrayList<CentroVaccinale> cercaCentro(String nome) throws RemoteException{
        return si.cercaCentroVaccinale(nome);
    }

    /**
     * Il metodo effettua una richiesta al server di ricerca del centro inserito in base al nome.
      * @param comune indica il comune del centro vaccinale.
     * @param tipologia indica la tipologia del centro vaccinale.
     * @return una lista dei centri vaccinali che soddisfano gli attributi inseriti.
     * @throws RemoteException
     */
    public ArrayList<CentroVaccinale> cercaCentro(String comune, String tipologia) throws RemoteException{
        return si.cercaCentroVaccinale(comune, tipologia);
    }

    /**
     * Il metodo controlla l'esistenza del centro vaccinale passato come argomento.
     * @param centroVaccinale il centro vaccinale di cui si vuole sapere l'esistenza.
     * @return indica true se il centro vaccinale esiste, false altrimenti.
     */
    public static boolean controllaCentro(CentroVaccinale centroVaccinale) {
        try {
            String errore = si.controllaCentroServer(centroVaccinale);
            if(!errore.equals("")){
                ConfirmBoxCentro.setError(errore);
                return false;
            }
        } catch (RemoteException e) {
            //e.printStackTrace();
            ConfirmBoxCentro.setError("Errore di connessione col Server");
            return false;
        }
        return true;
    }

    /**
     * Il metodo controlla l'esistenza del vaccinato passato come argomento.
     * @param vaccinato indica il vaccinato di cui si vuole sapere l'esistenza
     * @return true se il vaccinato esiste, false altrimenti.
     */
    public boolean controllaVaccinato(Vaccinato vaccinato) {
        String errore;
        try {
            errore = si.controllaVaccinatoServer(vaccinato);
        } catch (RemoteException e) {
            e.printStackTrace();
            ConfirmBoxCentro.setError("Errore di connessione con il Server");
            return false;
        }
        if(!errore.equals("")){
            ConfirmBoxVacc.setError(errore);
            return false;
        }
        return true;
    }
}