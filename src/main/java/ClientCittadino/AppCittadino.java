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
    private static int cittadino;
    public static ServerInterface si;
    private static Scene scene;
    private static Stage stage1;
    @Override
    public void start(Stage stage) throws Exception {
        stage1 = stage;
        scene = new Scene(loadFXML("cittadinoMainMenu.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("Menu");
        stage1.show();
    }
    static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppCittadino.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        connessione_server();
        launch();
    }

    public static void connessione_server() {
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
        System.out.println("Cittadino connesso al Server");
        try {
            cittadino = si.getCountC();
            System.out.println(cittadino + " connesso al Server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //chiede al server di registrare un cittadino
    public void registraCittadino(Cittadino cittadino) {
        System.out.println("Registrazione cittadino:");
        try {
            si.registraCittadino(cittadino);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(cittadino);
    }

    public boolean checkCittadino(Cittadino citt) {
        try {
            if(si.controllaCittadinoEsistenza(citt.idVacc,citt.codiceFiscale)){
                ConfirmBoxCittadino.error = "Cittadino già registrato!\nEffettua il login";
                return false;
            }
            if(!si.controllaCittadinoDatiPersonali(citt)){
                ConfirmBoxCittadino.error = "Nome o Cognome o Codice Fiscale errati!";
                return false;
            }
            if(si.controllaCittadinoEmail(citt.email)){
                ConfirmBoxCittadino.error = "Email già esistente!";
                return false;
            }
            if(si.controllaCittadinoUserId(citt.userid)){
                ConfirmBoxCittadino.error = "User ID già utilizzato!";
                return false;
            }
            if(!si.controllaCittadinoIDvacc(citt.idVacc, citt.codiceFiscale)){
                ConfirmBoxCittadino.error = "ID Vaccinazione sbagliato!";
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

