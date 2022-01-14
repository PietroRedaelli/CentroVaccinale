package ClientOperatoreSanitario;

import ClientCittadino.Cittadino;
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

public class OperatoreSanitarioAPP extends Application {

    private static OperatoreSanitario os = new OperatoreSanitario();
    private static ServerInterface si;
    private static Stage stage1;

    @Override
    public void start(Stage stage) throws IOException {
        stage1 = stage;
        Scene scene = new Scene(loadFXML("operatoreSceltaReg.fxml"));
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

    public static void main(String[] args){
        OperatoreSanitarioAPP app = new OperatoreSanitarioAPP();
        app.connessione_server();
        launch();
        app.disconnessione_server();
    }

    private void connessione_server(){
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client Operatore Sanitario: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
        try {
            os.setId(si.getCountOS());
            System.out.println(os +" connesso al Server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private void disconnessione_server() {
        try {
            si.diminuisciCountOS(os.getId());
            System.out.println(os +" disconnesso dal Server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void registraCentroVaccinale(CentroVaccinale centro){
        System.out.println(os+" registrazione Centro Vaccinale");
        try {
            si.registraCentroVaccinale(centro,os);
            System.out.println(centro + "\nCompletata registrazione Centro Vaccinale");
        } catch (RemoteException e) {
            System.out.println(os+ ": Error!!!\n" + e.getMessage());
        }
    }

    //metodo che controlla i campi del vaccinato con i dati nel databae e se tutto va bene registra il vaccinato
    public void registraVaccinato(Vaccinato vaccinato) {
        System.out.println(os+ " registrazione Vaccinato");
        try {
            si.registraVaccinato(vaccinato,os);
            System.out.println(vaccinato+"\nCompletata registrazione Vaccinato");
        } catch (RemoteException e) {
            e.printStackTrace();
            ConfirmBoxCentro.error = "Errore di connessione con il Server";
        }
    }

    //funzione che chiede al server di cercare il centro richiesto in base al nome
    public ArrayList<CentroVaccinale> cercaCentro(String nome) {
        ArrayList<CentroVaccinale> arrayListRicevuto = new ArrayList<>();
        try {
            arrayListRicevuto = si.cercaCentroVaccinale(nome);
        } catch (RemoteException e) {
            System.out.println(os+" : "+e.getMessage());
        }
        return arrayListRicevuto;
    }

    //funzione che chiede al server di cercare il centro richiesto in base al comune e alla tipologia
    public ArrayList<CentroVaccinale> cercaCentro(String comune, String tipologia) {
        ArrayList<CentroVaccinale> arrayListRicevuto = new ArrayList<>();
        try {
            arrayListRicevuto = si.cercaCentroVaccinale(comune, tipologia);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return arrayListRicevuto;
    }

    public static boolean controllaCentro(CentroVaccinale centroVaccinale) {
        try {
            String errore = si.controllaCentroServer(centroVaccinale);
            if(!errore.equals("")){
                ConfirmBoxCentro.error = errore;
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            ConfirmBoxCentro.error = "Errore di connessione con il Server";
            return false;
        }
        return true;
    }

    public boolean controllaVaccinato(Vaccinato vaccinato) {
        String errore;
        try {
            errore = si.controllaVaccinatoServer(vaccinato);
        } catch (RemoteException e) {
            e.printStackTrace();
            ConfirmBoxCentro.error = "Errore di connessione con il Server";
            return false;
        }
        if(!errore.equals("")){
            ConfirmBoxVacc.error = errore;
            return false;
        }
        return true;
    }
}
