package clientCVCittadino;

import clientCVOperatoreSanitario.CentroVaccinale;
import grafics.ConfirmBoxCittadino;
import serverCV.ServerInterface;
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
 * La classe permette di caricare l'interfaccia grafica di un clientCittadino.
 * @author Pietro
 * @version 1.0
 */
public class AppCittadino extends Application {

    private static Cittadino c = new Cittadino();
    public static ServerInterface si;
    private static Stage stage1;

    @Override
    public void start(Stage stage) throws Exception {
        stage1 = stage;
        Scene scene = new Scene(loadFXML("cittadinoLoading.fxml"));
        stage1.setScene(scene);
        stage1.setTitle("Cittadini");
        stage1.setResizable(false);
        stage1.show();
    }

    public static void setRoot(String fxml) throws IOException {
        stage1.setScene(new Scene(loadFXML(fxml)));
        stage1.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppCittadino.class.getClassLoader().getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        //connessione_server();
        launch();
    }

    /**
     * Il metodo stabilisce la connessione col server.
     * @return ritorna true se la connessione è andata a buon fine, false altrimenti.
     */
    public static boolean connessione_server() {
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            si = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            //System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            //System.exit(0);
            return false;
        }
        return true;
    }

    /**
     * Il metodo invia una richiesta al server di registrazione di un cittadino.
     */

    public void registraCittadino(Cittadino cittadino) {
        System.out.println("Registrazione cittadino: "+cittadino);
        try {
            si.registraCittadino(cittadino);
            c = cittadino;
            System.out.println("Registrazione cittadino: " + cittadino.getCodiceFiscale() + " avvenuta con successo");
            System.out.println(c.getCodiceFiscale() + " ha eseguito l'accesso");
        } catch (RemoteException e) {
            System.err.println("Client Cittadino: errore di connessione al server \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkCittadino(Cittadino citt) {
        try {
            if(si.controllaConnessione()){
                ConfirmBoxCittadino.setError(" Errore di Connessione con il Server! ");
                return false;
            }
            if(si.controllaCittadinoEsistenza(citt.getCodiceFiscale())){
                ConfirmBoxCittadino.setError(" Cittadino già registrato! \n Effettua il login! ");
                return false;
            }
            if(!si.controllaCittadinoDatiPersonali(citt)){
                ConfirmBoxCittadino.setError(" Dati Personali errati! ");
                return false;
            }
            if(si.controllaCittadinoEmail(citt.getEmail())){
                ConfirmBoxCittadino.setError(" Email già esistente ! \n Effettua il login! ");
                return false;
            }
            if(si.controllaCittadinoUserId(citt.getUserid())){
                ConfirmBoxCittadino.setError(" User ID già utilizzato, modifica!");
                return false;
            }
            if(!si.controllaCittadinoIDvacc(citt.getIdVacc(), citt.getCodiceFiscale())){
                ConfirmBoxCittadino.setError(" ID Vaccinazione sbagliato! ");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ConfirmBoxCittadino.setError(" Errore di connessione col server!");
            return false;
        }
        return true;
    }

    public ArrayList<CentroVaccinale> cercaCentro(String nome) throws RemoteException {
        return si.cercaCentroVaccinale(nome);
    }

    public ArrayList<CentroVaccinale> cercaCentro(String comune, String tipologia) throws RemoteException {
        return si.cercaCentroVaccinale(comune, tipologia);
    }
}