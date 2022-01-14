package ServerPackage;

import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.CentroVaccinale;
import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.Vaccinato;
import java.rmi.*;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    //Centro Vaccinale

    //Registrazione
    void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException;
    //Ricerca
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException;
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String comune, String tipologia) throws RemoteException;
    ArrayList<EventoAvverso> visualizzaInfoCentroVaccinale(int chiavePrimariaCentri) throws RemoteException;
    //Controllo
    String controllaCentroServer(CentroVaccinale cv) throws RemoteException;
    //Contatori Operatori Sanitari
    int getCountOS() throws RemoteException;
    void diminuisciCountOS(int id) throws RemoteException;

    //Vaccinato

    //Registrazione
    void registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException;
    //Check Vaccinato
    String controllaVaccinatoServer(Vaccinato vacc) throws RemoteException;

    //Cittadino
    void registraCittadino(Cittadino cittadino) throws RemoteException;
    //Ricerca

    //Controllo
    boolean controllaCittadinoDatiPersonali(Cittadino cittadino) throws RemoteException;
    boolean controllaCittadinoEmail(String email) throws RemoteException;
    boolean controllaCittadinoUserId(String UserID) throws RemoteException;
    boolean controllaCittadinoIDvacc(long IDvacc, String CodiceFiscale) throws RemoteException;
    boolean controllaCittadinoEsistenza(String CodiceFiscale) throws RemoteException;
    Cittadino controllaCittadinoLogin(String userid, String password, int countcittadino) throws RemoteException;
    Cittadino controllaCittadinoDose(Cittadino cittadino) throws RemoteException;
    //Contatori Cittadino
    int getCountC() throws RemoteException;
    void diminuisciCountC(int id) throws RemoteException;
    void logoutCittadino(String codiceFiscale, int countcittadino) throws RemoteException;

    //Evento Avverso
    String inserisciEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException;
    //Controllo
    boolean controllaEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException;

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere
    boolean controlloVaccCitt(Cittadino cittadino) throws RemoteException;


    CentroVaccinale cercaCentroVaccinale_CF(String CodiceFiscale)throws RemoteException;
    ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException;

    boolean controllaConnessione() throws RemoteException;


}

