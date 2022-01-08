package ClientCittadino;

import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import ClientOperatoreSanitario.Vaccinato;
import Grafics.ConfirmBoxCittadino;
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

public class AppCittadino  extends Application {
    public static Cittadino c = new Cittadino();
    public static int Countcittadino = 0;
    public static ServerInterface si;
    private static Scene scene;
    private static Stage stage1;

    @Override
    public void start(Stage stage) throws Exception {
        stage1 = stage;
        scene = new Scene(loadFXML("cittadinoMainMenu.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("Menu");
        stage1.setResizable(false);
        stage1.show();
    }
    static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        connessione_server();
        launch();
        disconnessione_server();
    }

    private static void connessione_server() {
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
        try {
            Countcittadino = si.getCountC();
            System.out.println("Cittadino(" + Countcittadino + ") connesso al Server");
        } catch (RemoteException e) {
            System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
    }

    private static void disconnessione_server() {
        try {
            si.diminuisciCountC(Countcittadino);
            System.out.println("Cittadino ("+Countcittadino +") disconnesso dal Server");
        } catch (RemoteException e) {
            System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
    }

    //chiede al server di registrare un cittadino
    public void registraCittadino(Cittadino cittadino) {
        System.out.println("Registrazione cittadino: "+cittadino);
        try {
            si.registraCittadino(cittadino);
            c = cittadino;
            System.out.println("Cittadino ("+Countcittadino+") registrazione cittadino: " + cittadino.getCodiceFiscale() + " avvenuta con successo");
            System.out.println("Cittadino ("+Countcittadino+") ha eseguito l'accesso come "+c.codiceFiscale);
        } catch (RemoteException e) {
            System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean checkCittadino(Cittadino citt) {
        try {
            if(si.controllaConnessione()){
                ConfirmBoxCittadino.error = " Errore di Connessione con il Server ! ";
                return false;
            }
            if(si.controllaCittadinoEsistenza(citt.codiceFiscale)){
                ConfirmBoxCittadino.error = " Cittadino già registrato ! \n Effettua il login ! ";
                return false;
            }
            if(!si.controllaCittadinoDatiPersonali(citt)){
                ConfirmBoxCittadino.error = " Dati Personali errati ! ";
                return false;
            }
            if(si.controllaCittadinoEmail(citt.email)){
                ConfirmBoxCittadino.error = " Email già esistente ! \n Effettua il login ! ";
                return false;
            }
            if(si.controllaCittadinoUserId(citt.userid)){
                ConfirmBoxCittadino.error = " User ID già utilizzato, modifica !";
                return false;
            }
            if(!si.controllaCittadinoIDvacc(citt.idVacc, citt.codiceFiscale)){
                ConfirmBoxCittadino.error = " ID Vaccinazione sbagliato ! ";
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

