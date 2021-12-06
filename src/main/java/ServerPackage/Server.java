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

//PIETRO HAI DA FARE ANCORA

public class Server extends UnicastRemoteObject implements ServerInterface{

    private static final long serialVersionUID = 1L;

    protected String url_DB = "jdbc:postgresql://localhost:5432/LabB" ;
    protected String user_DB = "postgres";
    protected String password_DB = "Xbox360Live" ;

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
            PreparedStatement statement = DB.prepareStatement("select * from \"CentriVaccinali\" where lower(nome) like ?");
            statement.setString(1, "%" + nomeCentro.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                String comune= resultSet.getString("comune");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(ID, centro, comune, nomeInd, civico, sigla, cap, tipo);
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
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String comuneInserito, String tipologia) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro

        ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("select * from \"CentriVaccinali\" where lower(comune) = ? and tipologia = ?");
            statement.setString(1, comuneInserito.toLowerCase());
            statement.setString(2, tipologia);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                String comune = resultSet.getString("comune");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(ID, centro, comune, nomeInd, civico, sigla, cap, tipo);
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
    public ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException{
        //Usando una query ricerchiamo dentro la tabella Vaccinato il codicefiscale del vaccinato

        ArrayList<Vaccinato> arrayListVaccinati = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("select * from \"Vaccinati\" where lower(codiceFisc) = ?");
            statement.setString(1, "%" + codiceFiscale.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ID = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                int centroVacc = resultSet.getInt("centroVacc");
                long idVacc = resultSet.getInt("idVacc");
                String codiceFisc = resultSet.getString("codiceFisc");
                String data = resultSet.getString("data");
                String vaccino = resultSet.getString("vaccino");
                int dose = resultSet.getInt("dose");
                Vaccinato vacc = new Vaccinato(ID, nome, cognome, centroVacc, idVacc, codiceFisc, data, vaccino, dose);
                arrayListVaccinati.add(vacc);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListVaccinati;
    }

    @Override
    public boolean controllaCentro(CentroVaccinale cv){

        //se il valore è 'true' allora il centro è già stato registrato
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"CentriVaccinali\" where lower(nome) = ? " +
                    "and lower(comune) = ? and lower(indirizzo) = ? and lower(civico) = ? and sigla = ? and cap = ? and tipologia = ?");
            statement.setString(1, cv.getNomeCentro().toLowerCase());
            statement.setString(2, cv.getComune().toLowerCase());
            statement.setString(3, cv.getIndirizzoCentro().toLowerCase());
            statement.setString(4, cv.getCivico().toLowerCase());
            statement.setString(5, cv.getSigla());
            statement.setInt(6, cv.getCap());
            statement.setString(7, cv.getTipo());
            ResultSet resultSet = statement.executeQuery();

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return esitoControllo;
    }

    @Override
    public boolean controllaVaccinato(Vaccinato vacc) {
        //se il valore è 'true' allora il vaccinato è già stato registrato
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where lower(nome) = ? " +
                    "and lower(cognome) = ? and fiscale = ? and centro = ? and giorno = ? and vaccino = ? and dose = ?");
            statement.setString(1, vacc.getNome().toLowerCase());
            statement.setString(2, vacc.getCognome().toLowerCase());
            statement.setString(3, vacc.getCodiceFisc());
            statement.setInt(4, vacc.getCentroVacc());
            statement.setString(5, vacc.getData());
            statement.setString(6, vacc.getVaccino());
            statement.setInt(7, vacc.getDose());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple >= 1) {
                    esitoControllo = true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esitoControllo;
    }

    //PIETRO: CONTROLLA CHE NON CI SIA UN CITTADINO UGUALE GIA' REGISTRATO

    @Override
    public boolean controllaCittadino(Cittadino cittadino) {
        //se il valore è 'true' allora il cittadino è già stato registrato
        boolean controlloEsistenza = false;
        boolean esisteUnDoppione = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Cittadini\" where lower(nome) = ? " +
                    "and lower(cognome) = ? and codiceFiscale = ? and email = ? and userid = ?");
            statement.setString(1, cittadino.getNome().toLowerCase());
            statement.setString(2, cittadino.getCognome().toLowerCase());
            statement.setString(3, cittadino.getCodiceFiscale());
            statement.setString(4, cittadino.getEmail());
            statement.setString(5, cittadino.getUserid());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple >= 1) {
                    controlloEsistenza = true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Cittadini\" where codiceFiscale = ?");
            statement.setString(3, cittadino.getCodiceFiscale());
            ResultSet resultSet = statement.executeQuery();
            while(!resultSet.toString().equals(cittadino.getIdVacc())){
                if(resultSet.toString().equals(cittadino.getIdVacc())) {
                    esisteUnDoppione = true;
                    break;
                }
                resultSet.next();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controlloEsistenza && !esisteUnDoppione;
    }

    //PIETRO E' TUO
    @Override
    public CentroVaccinale visualizzaInfoCentroVaccinale(CentroVaccinale centroVaccinaleSelezionato) throws RemoteException {

        //usando una query restituiamo le informazioni su un centro vaccianle. La classe centrovaccinale sarà in remoto

        CentroVaccinale centroVaccinale= null;
        PreparedStatement statement = null;
        try {
            if(centroVaccinaleSelezionato.getNomeCentro() != null) {
                statement = DB.prepareStatement("select * from \"CentriVaccinali\" where lower(nome) like ?");
                statement.setString(1, "%" + centroVaccinaleSelezionato.getNomeCentro().toLowerCase() + "%");
            } else{
                statement = DB.prepareStatement("select * from \"CentriVaccinali\" where lower(comune) = ? and tipologia = ?");
                statement.setString(1, centroVaccinaleSelezionato.getComune().toLowerCase());
                statement.setString(2, centroVaccinaleSelezionato.getTipo());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                String comune = resultSet.getString("comune");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                centroVaccinale = new CentroVaccinale(ID, centro, comune, nomeInd, civico, sigla, cap, tipo);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centroVaccinale;
    }

    //LUCA E' TUO
    @Override
    public String inserisciEventiAvversi(EventoAvverso eventoAvverso) throws RemoteException {
        //con una query un cittadino inserisce un evento avverso
        return "null";
    }

    @Override
    public void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException {
        //registrazzione del centro vaccinale
        String SQL = "INSERT INTO \"CentriVaccinali\"(nome, comune, indirizzo, civico, sigla, cap, tipologia) VALUES(?,?,?,?,?,?,?)";
         try {
            System.out.println(os + " registrazione CentroVaccinale: "+ centroVaccinale);
            PreparedStatement pstmt = DB.prepareStatement(SQL);
            pstmt.setString(1, centroVaccinale.getNomeCentro());
            pstmt.setString(2, centroVaccinale.getComune());
            pstmt.setString(3, centroVaccinale.getIndirizzoCentro());
            pstmt.setString(4, centroVaccinale.getCivico());
            pstmt.setString(5, centroVaccinale.getSigla());
            pstmt.setInt(6, centroVaccinale.getCap());
            pstmt.setString(7, centroVaccinale.getTipo());
            pstmt.executeUpdate();
            System.out.println(os + " registrazione Centro Vaccinale avvenuta con successo");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(os + ":" + e.getMessage());
        }
    }
    @Override
    public void registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException {
        //registrazzione di una persona vaccinata e si ritorna una stringa di conferma o di errore
        //in base al centro vaccinale in cui si registra il vaccinato bisogna verificare che la tabella esista
        //se nn esiste la tabella la creo e poi la popolo con il vaccinato
        //altrimenti inserisco il vaccinato nella tabella

        String SQL = "INSERT INTO \"Vaccinati\"(id, nome, cognome, fiscale, centro, giorno, vaccino, dose) VALUES(?,?,?,?,?,?,?,?)";
        try {
            System.out.println(os + " registrazione Vaccinato: " + vaccinato);
            PreparedStatement pstmt = DB.prepareStatement(SQL);    //, Statement.RETURN_GENERATED_KEYS
            pstmt.setLong(1,vaccinato.getIdVacc());
            pstmt.setString(2, vaccinato.getNome());
            pstmt.setString(3, vaccinato.getCognome());
            pstmt.setString(4, vaccinato.getCodiceFisc());
            pstmt.setInt(5, vaccinato.getCentroVacc());
            pstmt.setString(6, vaccinato.getData());
            pstmt.setString(7, vaccinato.getVaccino());
            pstmt.setInt(8, vaccinato.getDose());
            pstmt.executeUpdate();
            System.out.println(os + " registrazione Vaccinato avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(os + ":\n" + e.getMessage());
        }

        System.out.println("metodo registra Vaccinato");
    }
    @Override
    public void registraCittadino(Cittadino cittadino) throws RemoteException{
        String SQL = "INSERT INTO \"Cittadino\"(id, nome, cognome, codiceFiscale, email, userid, passowrd, idVacc) VALUES(?,?,?,?,?,?,?,?)";
        try {
            System.out.println(" registrazione Cittadino: " + cittadino);
            PreparedStatement pstmt = DB.prepareStatement(SQL);    //, Statement.RETURN_GENERATED_KEYS
            pstmt.setString(1,cittadino.getIdVacc());
            pstmt.setString(2, cittadino.getNome());
            pstmt.setString(3, cittadino.getCognome());
            pstmt.setString(4, cittadino.getCodiceFiscale());
            pstmt.setString(5, cittadino.getEmail());
            pstmt.setString(6, cittadino.getUserid());
            pstmt.setString(7, cittadino.getPassword());
            pstmt.setString(8, cittadino.getIdVacc());
            pstmt.executeUpdate();
            System.out.println("registrazione Cittadino avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(cittadino + ":\n" + e.getMessage());
        }

        System.out.println("metodo registra Cittadino");
    }

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere
    @Override
    public boolean controlloVaccCitt(Cittadino cittadino){
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" JOIN \"Cittadino\" where lower(ID) = lower(idVacc)");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple >= 1) {
                    esitoControllo = true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esitoControllo;
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
            PreparedStatement statement = DB.prepareStatement("select * from \"CentriVaccinali\"");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String nomeCentro = resultSet.getString("nome");
                String comune= resultSet.getString("comune");
                String nomeInd = resultSet.getString("indirizzo");
                String civico = resultSet.getString("civico");
                String sigla = resultSet.getString("sigla");
                int cap = resultSet.getInt("cap");
                String tipo = resultSet.getString("tipologia");
                CentroVaccinale cv = new CentroVaccinale(nomeCentro, comune, nomeInd, civico, sigla, cap, tipo);
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
            ResultSet resultSet = statement.executeQuery("select * from vaccinati_"+ v.getCentroVacc());
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void createTableVaccinati(Vaccinato v){
        String queryTable = "CREATE TABLE Vaccinati_"+v.getCentroVacc()+" (" +
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