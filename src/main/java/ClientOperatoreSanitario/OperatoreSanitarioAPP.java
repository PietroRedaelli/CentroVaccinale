package ClientOperatoreSanitario;

import ServerPackage.CentroVaccinale;
import ServerPackage.ServerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OperatoreSanitarioAPP extends Application {

    private static OperatoreSanitario os = new OperatoreSanitario();
    private static ServerInterface si;
    private static Stage stage1;
    @FXML private Button BTRegCentro;
    @FXML private Button BTRegVacc;


    public static void main(String[] args){
        //connessione_server();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage1 = stage;
        //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("operatoreSceltaReg.fxml"));
        //Parent root = loader.load();
        Scene scene = new Scene(loadFXML("operatoreSceltaReg.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("CentroVaccinale");
        stage1.show();
        stage1.setMinHeight(400);
        stage1.setMinWidth(600);
        stage1.setMaxHeight(800);
        stage1.setMaxWidth(1000);
        //stage1.setMaximized(true);    per far partire l'app direttamente in fullscreen
    }

    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OperatoreSanitarioAPP.class.getClassLoader().getResource(fxml));
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

    public void registraCV(CentroVaccinale centro){
        System.out.println("registrazione CV");
        try {
            si.registraCentroVaccinale(centro,os);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registrato "+centro);
    }

    public void registraVaccinato(Vaccinato vaccinato) {
        System.out.println("registrazione Vaccinato");
        try {
            si.registraVaccinato(vaccinato,os);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registrato "+vaccinato);
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
