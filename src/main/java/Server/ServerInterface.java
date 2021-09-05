package Server;

import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.Vaccinato;

import java.rmi.*;
import java.util.ArrayList;


public interface ServerInterface extends Remote {

    ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException;
    ArrayList<CentroVaccinale> cercaCentroVaccinale(String comune, String tipologia) throws RemoteException;

    CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException;
    String registraCittadino(Cittadino cittadino) throws RemoteException;
    String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException;


    String registraCentroVaccinale(CentroVaccinale centroVaccinale) throws RemoteException;
    String registraVaccinato(Vaccinato vaccinato) throws RemoteException;


    /*
    void richiediNotizia(ClientInterface fr, TipoNotizia tipoNotizia) throws RemoteException;
    void disdici(ClientInterface fr, TipoNotizia tipoNotizia) throws RemoteException;
    int getCount() throws RemoteException;
    void decrementoCount(String fr) throws RemoteException;*/
}

