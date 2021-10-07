package ServerPackage;

import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.Vaccinato;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.sql.*;

public class Server extends UnicastRemoteObject implements ServerInterface{

    private static final long serialVersionUID = 1L;

    protected String url_DB = "jdbc:postgresql://localhost:5432/LabB" ;
    protected String user_DB = "postgres";
    protected String password_DB = "F4/=rb91d&w3" ;

    protected static Connection DB = null;


    int countC = 0;
    int countOS = 0;

    public Server() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro

        ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            PreparedStatement statement1 = DB.prepareStatement("select * from centri_vaccinali where nome like ?");
            statement1.setString(1, "%" + nomeCentro + "%");
            ResultSet resultSet = statement1.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                String comune= resultSet.getString("comune");
                String qualif = resultSet.getString("qualificatore");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("numero_civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(ID, centro,comune,qualif,nomeInd,civico,sigla,cap,tipo);
                arrayListCentri.add(cv);
            }
            resultSet.close();
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListCentri;
    }
    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String comuneInserito, String tipologia) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro

        ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            Statement statement = DB.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from centri_vaccinali where comune = " + comuneInserito + " and tipologia = " + tipologia);
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                String comune = resultSet.getString("comune");
                String qualif = resultSet.getString("qualificatore");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("numero_civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(ID, centro,comune,qualif,nomeInd,civico,sigla,cap,tipo);
                arrayListCentri.add(cv);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListCentri;
    }
    @Override
    public CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException {
        //usando una query restituiamo le informazioni su un centro vaccianle. La classe centrovaccinale sarà in remoto
        return new CentroVaccinale();
    }
    @Override
    public String registraCittadino(Cittadino cittadino) throws RemoteException {
        //ritorna vero se è andato a buon fine, falso se esiste un cittadino registrato
        return "null";
    }
    @Override
    public String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException {
        //con una query un cittadino inserisce un evento avverso
        return "null";
    }
    @Override
    public void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException {
        //registrazzione del centro vaccinale
        String SQL = "INSERT INTO centri_vaccinali(nome, comune, qualificatore, indirizzo, numero_civico, sigla, cap, tipologia) VALUES(?,?,?,?,?,?,?,?)";
        try {
            System.out.println(os + " registrazioneCentroVaccinale: "+ centroVaccinale);
            PreparedStatement pstmt = DB.prepareStatement(SQL);
            pstmt.setString(1, centroVaccinale.getNomeCentro());
            pstmt.setString(2, centroVaccinale.getComune());
            pstmt.setString(3, centroVaccinale.getQualif());
            pstmt.setString(4, centroVaccinale.getNomeInd());
            pstmt.setString(5, centroVaccinale.getCivico());
            pstmt.setString(6, centroVaccinale.getSigla());
            pstmt.setInt(7, centroVaccinale.getCap());
            pstmt.setString(8, centroVaccinale.getTipo());
            pstmt.executeUpdate();
            System.out.println(os + " registrazioneCentroVaccinale avvenuta con successo");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(os + ":" + e.getMessage());
        }
    }
    @Override
    public String registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException {
        //registrazzione di una persona vaccinata e si ritorna una stringa di conferma o di errore
        //in base al centro vaccinale in cui si registra il vaccinato bisogna verificare che la tabella esista
        //se nn esiste la tabella la creo e poi la popolo con il vaccinato
        //altrimenti inserisco il vaccinato nella tabella
        if(check_tabella_CV(vaccinato)){
            //esiste tabella allora inserisco il vaccinato
            String SQL = "INSERT INTO "+vaccinato.getNomeCentro()+"  VALUES(?)";
            try {
                System.out.println(os + " registrazioneVaccinato: "+ vaccinato);
                PreparedStatement pstmt = DB.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, vaccinato.getNomeCentro());
                pstmt.executeUpdate();
                System.out.println(os + " registrazioneVaccinato avvenuta con successo");
            } catch (SQLException e) {
                System.out.println(os + ":\n" + e.getMessage());
            }
        }else{
            //non esiste tabella allora:
            //1. creo tabella
            //2. inserisco tupla

        }
        System.out.println("metodo registra Vaccinato");
        return "null";
    }


    @Override
    public int getCountC() throws RemoteException {
        countC++;
        System.out.println("Cittadino "+ countC +": connesso");
        return countC;
    }
    @Override
    public OperatoreSanitario getCountOS(OperatoreSanitario os) throws RemoteException {
        countOS++;
        os.setId(countOS);
        System.out.println(os + ": connesso");
        return os;
    }

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        server.connessione_server();
        server.connessione_DB();
        server.query();
        //server.queryEliminareTupla("Azzate");
        //server.query();

    }

    private void queryEliminareTupla(String azzate) {

        try {
            String SQL = "DELETE FROM centri_vaccinali WHERE nome = 'fsa'";
            Statement pstmt = DB.createStatement();
            pstmt.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void connessione_server(){
        try {
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("CentroVaccinale", this);
            System.out.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Errore nella creazione del registro: \n"+e.getMessage());
            System.exit(0);
        }
    }
    private void connessione_DB(){
        try {
            DB = DriverManager.getConnection(url_DB,user_DB,password_DB);
            if(DB != null){
                System.out.println("Connessione avvenuta al DB");
            }else{
                System.out.println("Connessione fallita al DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void query() {
        try {
            Statement statement = DB.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from centri_vaccinali");
            while(resultSet.next()){
                String nomeCentro = resultSet.getString("nome");
                String comune= resultSet.getString("comune");
                String qualif = resultSet.getString("qualificatore");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("numero_civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(nomeCentro,comune,qualif,nomeInd,civico,sigla,cap,tipo);
                System.out.println( cv );
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean check_tabella_CV(Vaccinato v) {
        try {
            Statement statement = DB.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vaccinati_"+ v.getNomeCentro());
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void createTableVaccinati(Vaccinato v){
        String queryTable = "CREATE TABLE Vaccinati_"+v.getNomeCentro()+" (" +
                "    NomeCentro varchar(50) NOT NULL," +
                "    Nome_Cognome_Vaccinato varchar(50) NOT NULL," +
                "    CF varchar(20) NOT NULL," +
                "    Data_somministrazione data" +
                "    PRIMARY KEY (), ()" +
                "    FOREIGN KEY (NomeCentro) REFERENCES centri_vaccinali(Nome)\n" +
                ");";
        try {
            Statement statement = DB.createStatement();
            ResultSet resultSet = statement.executeQuery(queryTable);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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