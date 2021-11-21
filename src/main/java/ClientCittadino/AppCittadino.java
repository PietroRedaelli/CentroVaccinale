package ClientCittadino;

import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import ClientOperatoreSanitario.Vaccinato;
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
    private static ServerInterface si;
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
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getClassLoader().getResource(fxml));
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
        System.out.println("registrazione cittadino");
        try {
            si.registraCittadino(cittadino);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registrato "+cittadino);
    }

    public boolean controllaEsistenzaCittadino(Cittadino citt) {
        System.out.println("controllo esistenza cittadino");
        boolean risultato = false;
        try {
            risultato = si.controllaCittadino(citt);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return risultato;
    }
}

