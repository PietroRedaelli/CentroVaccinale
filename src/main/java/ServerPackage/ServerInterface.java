package ServerPackage;

import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.Vaccinato;
import java.rmi.*;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException;
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String comune, String tipologia) throws RemoteException;
    CentroVaccinale cercaCentroVaccinale_CF(String CodiceFiscale)throws RemoteException;
    ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException;

    //Check Centro
    boolean controllaCentro(CentroVaccinale cv) throws RemoteException;

    //Check Vaccinato
    boolean controllaVaccinato(Vaccinato vacc) throws RemoteException;

    //Check Cittadino
    boolean controllaCittadinoDatiPersonali(Cittadino cittadino) throws RemoteException;
    boolean controllaCittadinoEmail(String email) throws RemoteException;
    boolean controllaCittadinoUserId(String UserID) throws RemoteException;
    boolean controllaCittadinoIDvacc(long IDvacc, String CodiceFiscale) throws RemoteException;
    boolean controllaCittadinoEsistenza(long IDvacc, String CodiceFiscale) throws RemoteException;
    Cittadino controllaCittadinoLogin(String userid, String password) throws RemoteException;

    //Check Evento Avverso

    CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException;
    String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException;


    void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException;
    void registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException;
    void registraCittadino(Cittadino cittadino) throws RemoteException;

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere
    boolean controlloVaccCitt(Cittadino cittadino) throws RemoteException;

    int getCountC() throws RemoteException;

    OperatoreSanitario getCountOS(OperatoreSanitario os) throws RemoteException;


}

