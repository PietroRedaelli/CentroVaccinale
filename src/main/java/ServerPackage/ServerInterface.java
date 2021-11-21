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
    ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException;

    boolean controllaCentro(CentroVaccinale cv) throws RemoteException;
    boolean controllaVaccinato(Vaccinato vacc) throws RemoteException;

    CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException;
    String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException;


    void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException;
    void registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException;
    void registraCittadino(Cittadino cittadino) throws RemoteException;

    int getCountC() throws RemoteException;

    OperatoreSanitario getCountOS(OperatoreSanitario os) throws RemoteException;
}

