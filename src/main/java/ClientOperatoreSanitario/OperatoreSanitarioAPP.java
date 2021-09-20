package ClientOperatoreSanitario;

import ServerPackage.CentroVaccinale;
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

public class OperatoreSanitarioAPP extends Application {

    private static OperatoreSanitario os = new OperatoreSanitario();
    private static ServerInterface si;
    int id;
    private static Scene scene;


    public static void main(String[] args){
        connessione_server();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void connessione_server(){
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operatore connesso al Server");
        try {
            os = si.getCountOS(os);
            System.out.println(os +" connesso al Server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registraCV( ){
        System.out.println("registrazione CV");
        CentroVaccinale cv = new CentroVaccinale("San Carlo");
        try {
            si.registraCentroVaccinale(os, cv);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registrato "+cv);
    }

    public void registraVaccinato() {
        System.out.println("registrazione Vaccinato");
        Vaccinato v = new Vaccinato();
        try {
            si.registraVaccinato(v,os);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registrato "+v);
    }

/*
    int id;
    Notizia notiziaPolitica;
    Notizia notiziaAttualita;
    Notizia notiziaScienza;
    Notizia notiziaSport;

    public OperatoreSanitario(int id) {
        super("Produttore Notizie "+id);
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            creaNotizia();//genera notizie
            try {
                Thread.sleep(new Random().nextInt(3000)+1000);//attesa casuale
            } catch (InterruptedException e) {}
        }
    }

    private void creaNotizia(){
        String contenuto = "Notizia generata da " + getName();
        creaNotiziaPolitica(contenuto);
        creaNotiziaAttualita(contenuto);
        creaNotiziaScienza(contenuto);
        creaNotiziaSport(contenuto);
    }
    private void creaNotiziaPolitica(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Politica
            notiziaPolitica = new Notizia(TipoNotizia.POLITICA, contenuto);
        }
    }
    private void creaNotiziaAttualita(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Attualità
            notiziaAttualita = new Notizia(TipoNotizia.ATTUALITA, contenuto);
        }
    }
    private void creaNotiziaScienza(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Scienza
            notiziaScienza = new Notizia(TipoNotizia.SCIENZA, contenuto);
        }
    }
    private void creaNotiziaSport(String contenuto) {
        if((new Random().nextInt(100))%10 == 0){//crea notizia Sport
            notiziaSport = new Notizia(TipoNotizia.SPORT, contenuto);
        }
    }*/
}
