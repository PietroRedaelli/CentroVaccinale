package Server;


import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.Vaccinato;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerInterface{

    private static final long serialVersionUID = 1L;

    public Server() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro
        return null;
    }


    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String comune, String tipologia) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro
        return null;
    }

    @Override
    public CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException {
        //usando una query restituiamo le informazioni su un centro vaccianle. La classe centrovaccinale sarà in remoto
        return null;
    }


    @Override
    public String registraCittadino(Cittadino cittadino) throws RemoteException {
        //ritorna vero se è andato a buon fine, falso se esiste un cittadino registrato
        return null;
    }

    @Override
    public String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException {
        //con una query un cittadino inserisce un evento avverso
        return null;
    }

    @Override
    public String registraCentroVaccinale(CentroVaccinale centroVaccinale) throws RemoteException {
        //registrazzione del centro vaccinale e si ritorna una stringa di conferma o di errore
        return null;
    }

    @Override
    public String registraVaccinato(Vaccinato vaccinato) throws RemoteException {
        //registrazzione di una persona vaccinata e si ritorna una stringa di conferma o di errore

        return null;
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("CentroVaccinale", server);
        } catch (RemoteException e) {
            System.err.println("Errore nella creazione del registro: \n"+e.getMessage());
            System.exit(0);
        }
        System.out.println("Server ready");
    }

    /*
    private ArrayList<ClientInterface> politica;
    private ArrayList<ClientInterface> attualita;
    private ArrayList<ClientInterface> scienza;
    private ArrayList<ClientInterface> sport;
    private ArrayList<String> numList = new ArrayList<>();

    private Notizia nP;
    private Notizia nA;
    private Notizia nS;
    private Notizia nSP;
    private ProduttoreNotizie[] p = new ProduttoreNotizie[5];

    private int count = 1;

    Pubblicatore() throws RemoteException{
        super();
    }

    private void exec() {
        creaListeTipiNotizie();
        try {
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("Pubblicatore", this);
        } catch (RemoteException e) {
            System.err.println("Errore nella creazione del registro: \n"+e.getMessage());
            System.exit(0);
        }
        System.out.println("Server ready");
        for (int i = 0 ; i <= 4; i++) {
            p[i] = new ProduttoreNotizie(i+1);
            p[i].start();
        }
        while(true){
            notifyClient();//notifica i clienti
            try {
                Thread.sleep(5000);//attesa di P = 5 sec
            } catch (InterruptedException e) {}
        }
    }
    private synchronized void notifyClient() {
        getNotizie();
        notifyFruitore(nP, politica);
        notifyFruitore(nA, attualita);
        notifyFruitore(nS, scienza);
        notifyFruitore(nSP, sport);
        azzeraNotizie();
    }
    private void getNotizie(){
        for(int i = 0; i < p.length ; i++){
            if(nP == null)
                nP = p[i].notiziaPolitica;
            if(nA == null)
                nA = p[i].notiziaAttualita;
            if(nS == null)
                nS = p[i].notiziaScienza;
            if(nSP == null)
                nSP = p[i].notiziaSport;
        }
    }
    private void notifyFruitore(Notizia notizia, ArrayList<ClientInterface> list) {
        if (notizia != null){
            ClientInterface fr = null;
            for(int i = 0; i < list.size() ; i++){
                try {
                    fr = list.get(i);
                    System.out.println(notizia + " a " + fr.getNome());
                    fr.notifica(notizia);
                } catch (RemoteException e) {
                    System.err.println("Disconnesione improvvisa di un Fruitore: tolto dalla lista "+notizia.tipoNotizia);
                    //togliete fruitore dalla lista
                    list.remove(fr);
                }
            }
        }
    }
    private void creaListeTipiNotizie() {
        politica = new ArrayList<>();
        attualita = new ArrayList<>();
        scienza = new ArrayList<>();
        sport = new ArrayList<>();
    }
    private void azzeraNotizie() {
        for(int i = 0; i < p.length ; i++){
            if (nP == p[i].notiziaPolitica)
                p[i].notiziaPolitica = null;
            if (nA == p[i].notiziaAttualita)
                p[i].notiziaAttualita = null;
            if (nS == p[i].notiziaScienza)
                p[i].notiziaScienza = null;
            if (nSP == p[i].notiziaSport)
                p[i].notiziaSport = null;
        }
        nP = null;
        nA = null;
        nS = null;
        nSP = null;
    }

    public static void main(String[] args) {
        try {
            new Pubblicatore().exec();
        } catch (RemoteException e) {
            System.err.println("Chiusura server: Errore nel creazione del RemoteObject\n"+e.getMessage());
            System.exit(0);
        }
    }
    @Override
    public synchronized void richiediNotizia(ClientInterface fr, TipoNotizia tipoNotizia) throws RemoteException {
        System.out.println("Abbonato "+fr.getNome()+" a "+tipoNotizia);
        switch (tipoNotizia){
            case POLITICA:
                politica.add(fr);
                break;
            case ATTUALITA:
                attualita.add(fr);
                break;
            case SCIENZA:
                scienza.add(fr);
                break;
            case SPORT:
                sport.add(fr);
                break;
        }
    }
    @Override
    public synchronized void disdici(ClientInterface fr, TipoNotizia tipoNotizia) throws RemoteException {
        System.out.println("Uscito "+fr.getNome()+" da "+tipoNotizia);
        switch (tipoNotizia){
            case POLITICA:
                politica.remove(fr);
                break;
            case ATTUALITA:
                attualita.remove(fr);
                break;
            case SCIENZA:
                scienza.remove(fr);
                break;
            case SPORT:
                sport.remove(fr);
                break;
        }
    }
    @Override
    public synchronized int getCount() throws RemoteException {
        int i = 1;
        while(numList.contains("Fruitore "+i)){
            i++;
        }
        numList.add("Fruitore "+i);
        if(i == count){
            System.out.println("Entrato nel server Fruitore "+(count+1));
            return count++;
        }
        else{
            System.out.println("Entrato nel server Fruitore "+i);
            return i;
        }
    }

    @Override
    public synchronized void decrementoCount(String fr) throws RemoteException {
        numList.remove(fr);
        count--;
        System.out.println("Disconnesso: " + fr);
    }
    */
}