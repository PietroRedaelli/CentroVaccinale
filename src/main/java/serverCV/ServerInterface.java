package serverCV;

import clientCVCittadino.Cittadino;
import clientCVCittadino.EventoAvverso;
import clientCVOperatoreSanitario.CentroVaccinale;
import clientCVOperatoreSanitario.Vaccinato;
import java.rmi.*;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    //Centro Vaccinale
    //Registrazione centro
    void registraCentroVaccinale(CentroVaccinale centroVaccinale) throws RemoteException;
    //Ricerca dei centri
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException;
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String comune, String tipologia) throws RemoteException;
    //Evita che un centro già registrato no venga registrato di nuovo
    String controllaCentroServer(CentroVaccinale cv) throws RemoteException;


    //Vaccinato
    //Registrazione
    void registraVaccinato(Vaccinato vaccinato) throws RemoteException;
    //Controlli nella registrazione di un vaccinato
    String controllaVaccinatoServer(Vaccinato vacc) throws RemoteException;



    //Cittadino
    //Registrazione cittadino
    void registraCittadino(Cittadino cittadino) throws RemoteException;

    //Controlli nella registrazione del cittadino
    boolean controllaCittadinoDatiPersonali(Cittadino cittadino) throws RemoteException;
    boolean controllaCittadinoEmail(String email) throws RemoteException;
    boolean controllaCittadinoUserId(String UserID) throws RemoteException;
    boolean controllaCittadinoIDvacc(long IDvacc, String CodiceFiscale) throws RemoteException;
    boolean controllaCittadinoEsistenza(String CodiceFiscale) throws RemoteException;
    Cittadino controllaCittadinoLogin(String userid, String password) throws RemoteException;
    Cittadino controllaCittadinoDose(Cittadino cittadino) throws RemoteException;


    //Evento Avverso
    //Registrazione evento avverso
    String inserisciEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException;
    //Ricerca eventi avversi
    ArrayList<EventoAvverso> visualizzaInfoCentroVaccinale(int chiavePrimariaCentri) throws RemoteException;
    //Controllo
    boolean controllaEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException;

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere
    boolean controlloVaccCitt(Cittadino cittadino) throws RemoteException;


    CentroVaccinale cercaCentroVaccinale_CF(String CodiceFiscale)throws RemoteException;
    ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException;

    boolean controllaConnessione() throws RemoteException;
}