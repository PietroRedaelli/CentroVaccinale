package ServerPackage;

import ClientCittadino.AppCittadino;
import ClientCittadino.Cittadino;
import ClientCittadino.EventoAvverso;
import ClientOperatoreSanitario.CentroVaccinale;
import ClientOperatoreSanitario.OperatoreSanitario;
import ClientOperatoreSanitario.Vaccinato;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.*;

public class Server extends UnicastRemoteObject implements ServerInterface{

    private static final long serialVersionUID = 1L;

    protected String url_DB = "jdbc:postgresql://localhost:5432/LabB" ;
    protected String user_DB = "postgres";
    protected String password_DB = "F4/=rb91d&w3" ;

    protected Connection DB = null;

    private ArrayList<Integer> OSconnessi = new ArrayList<>();
    private ArrayList<Integer> OSdisconnesi = new ArrayList<>();
    private ArrayList<Integer> Cconnessi = new ArrayList<>();
    private ArrayList<Integer> Cdisconnessi = new ArrayList<>();
    int countC = 0;
    int countOS = 0;

    public Server() throws RemoteException {
        super();
    }

    //Centri Vaccinali

    //Registrazione di un Centro Vaccinale
    @Override
    public void registraCentroVaccinale(CentroVaccinale centroVaccinale,OperatoreSanitario os) throws RemoteException {
        //registrazzione del centro vaccinale
        String SQL = "INSERT INTO \"CentriVaccinali\"(nome, comune, indirizzo, civico, sigla, cap, tipologia) VALUES(?,?,?,?,?,?,?)";
        try {
            System.out.println(os + " registrazione CentroVaccinale: \n" + centroVaccinale);
            PreparedStatement pstmt = DB.prepareStatement(SQL);
            pstmt.setString(1, centroVaccinale.getNomeCentro());
            pstmt.setString(2, centroVaccinale.getComune());
            pstmt.setString(3, centroVaccinale.getIndirizzoCentro());
            pstmt.setString(4, centroVaccinale.getCivico());
            pstmt.setString(5, centroVaccinale.getSigla());
            pstmt.setInt(6, centroVaccinale.getCap());
            pstmt.setString(7, centroVaccinale.getTipo());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println(os + " registrazione Centro Vaccinale avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(os + ":" + e.getMessage());
        }
    }
    //Ricerca centro vaccinale per Nome
    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale il nome del centro

        ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM \"CentriVaccinali\" WHERE lower(nome) LIKE ? ORDER BY lower(nome)");
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
    //Ricerca centro vaccinale per Comune e Tipologia
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
    //check del centro che si vuole registrare; implementato per Indirizzo e Città
    @Override
    public String controllaCentroServer(CentroVaccinale cv) throws RemoteException{

        String esitoControllo = "";

        try {

            PreparedStatement statement = DB.prepareStatement("select count(*) from \"CentriVaccinali\" where "+
                    "lower(comune) = ? and lower(indirizzo) = ? and lower(civico) = ?");
            statement.setString(1, cv.getComune().toLowerCase());
            statement.setString(2, cv.getIndirizzoCentro().toLowerCase());
            statement.setString(3, cv.getCivico().toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple >= 1) {
                    esitoControllo = "! Esiste già un centro a questo indirizzo !";
                }
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {

            e.printStackTrace();
            return "! Errore di connessione con il Server !";

        }

        return esitoControllo;
    }
    //metodo che restituisce il numero per l'Operatoro Sanitario che si è appena connesso
    @Override
    public int getCountOS() throws RemoteException {
        if(!OSdisconnesi.isEmpty()){
            int num = OSdisconnesi.remove(0);
            OSconnessi.add(num-1,num);
            return num;
        }
        countOS++;
        OSconnessi.add(countOS-1,countOS);
        System.out.println("Operatore Sanitario (" +countOS+ "): connesso");
        return countOS;
    }
    //metodo permette di togliere il numero dell'Operatore Sanitario che si è appena scollegato
    @Override
    public void diminuisciCountOS(int id) throws RemoteException {
        OSdisconnesi.add(OSconnessi.remove(id-1));
        System.out.println("Operatore Sanitario (" +countOS+ "): disconnesso");
    }

    //Vaccinato

    //Registrazione di un Vaccinato
    @Override
    public void registraVaccinato(Vaccinato vaccinato, OperatoreSanitario os) throws RemoteException {
        //registrazzione di una persona vaccinata e si ritorna una stringa di conferma o di errore
        //in base al centro vaccinale in cui si registra il vaccinato bisogna verificare che la tabella esista
        //se nn esiste la tabella la creo e poi la popolo con il vaccinato
        //altrimenti inserisco il vaccinato nella tabella

        String SQL = "INSERT INTO \"Vaccinati\"(idvacc, nome, cognome, fiscale, centro, giorno, vaccino, dose) VALUES(?,?,?,?,?,?,?,?)";
        try {
            System.out.println(os + " registrazione Vaccinato: \n" + vaccinato);
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
            pstmt.close();
            System.out.println(os + " registrazione Vaccinato avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(os + ":\n" + e.getMessage());
        }
    }
    //check del vaccinato che si vuole registrare (per tutti i campi)
    @Override
    public String controllaVaccinatoServer(Vaccinato vacc) throws RemoteException{

        String esitoControllo = "";

        //codice fiscale
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where fiscale = ? ");
            statement.setString(1, vacc.getCodiceFisc());
            ResultSet resultSet = statement.executeQuery();
            int numeroTuple = 0;
            if (resultSet.next()) {
                numeroTuple = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            if (numeroTuple == 0) {
                if(vacc.getDose() != 1) esitoControllo = "! Dose inserita sbagliata !";
                return esitoControllo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            esitoControllo = "Errore di connessione con il Server ";
            return esitoControllo;
        }

        //data
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where " +
                    "fiscale = ? and giorno >= ? ");
            statement.setString(1, vacc.getCodiceFisc());
            statement.setString(2, vacc.getData());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple >= 1) {
                    esitoControllo = "! Esiste già una data di vaccinazione più recente !";
                }
            }
            resultSet.close();
            statement.close();
            if(!esitoControllo.isEmpty()) return esitoControllo;
        } catch (SQLException e) {
            e.printStackTrace();
            esitoControllo = "Errore di connessione con il Server ";
            return esitoControllo;
        }

        //dose
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where " +
                    "fiscale = ? and dose = ?");
            statement.setString(1, vacc.getCodiceFisc());
            statement.setInt(2, vacc.getDose());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int numeroTuple = resultSet.getInt(1);
                if (numeroTuple == 1) {
                    esitoControllo = "! Dose "+vacc.getDose()+" già fatta !";
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            esitoControllo = "Errore di connessione con il Server ";
            return esitoControllo;
        }
        return esitoControllo;
    }

    //Cittadino

    //Registrazione di un Cittadino
    @Override
    public void registraCittadino(Cittadino cittadino) throws RemoteException{
        String SQL = "INSERT INTO \"Cittadino\"(idvacc, nome, cognome, codiceFiscale, email, userid, password) VALUES(?,?,?,?,?,?,?)";
        try {
            System.out.println("Registrazione Cittadino: \n" + cittadino);
            PreparedStatement pstmt = DB.prepareStatement(SQL);
            pstmt.setLong(1,cittadino.getIdVacc());
            pstmt.setString(2, cittadino.getNome());
            pstmt.setString(3, cittadino.getCognome());
            pstmt.setString(4, cittadino.getCodiceFiscale());
            pstmt.setString(5, cittadino.getEmail());
            pstmt.setString(6, cittadino.getUserid());
            pstmt.setString(7, cittadino.getPassword());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("registrazione Cittadino avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(cittadino + ":\n" + e.getMessage());
        }
    }
    //DA RIGUARDARE IL CONTROLLI
    //controllo che esista il cittadino nella tabella vaccinati (nome,cognome,codice fiscale);
    @Override
    public boolean controllaCittadinoDatiPersonali(Cittadino cittadino) throws RemoteException {
        //se il valore è 'true' allora il cittadino è già stato registrato
        boolean esitoControllo = false;

        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where lower(nome) = ? and lower(cognome) = ? and fiscale = ?");
            statement.setString(1, cittadino.getNome().toLowerCase());
            statement.setString(2, cittadino.getCognome().toLowerCase());
            statement.setString(3, cittadino.getCodiceFiscale());
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
            esitoControllo = false;
        }
        return esitoControllo;
    }
    //controllo che esista non esista già un cittadino con la mail inserita;
    @Override
    public boolean controllaCittadinoEmail(String email) throws RemoteException {
        //se il valore è 'true' allora esiste un cittadino con questa mail
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Cittadino\" where lower(email) = ?");
            statement.setString(1, email.toLowerCase());
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
            esitoControllo = false;
        }
        return esitoControllo;
    }
    //controllo che esista non esista già un cittadino con la User ID inserito;
    @Override
    public boolean controllaCittadinoUserId(String UserID) throws RemoteException {
        //se il valore è 'true' allora esiste un cittadino con questo User ID
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Cittadino\" where userid = ?");
            statement.setString(1, UserID);
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
    //controllo che ID Vaccinazione sia corretto;
    @Override
    public boolean controllaCittadinoIDvacc(long IDvacc, String CodiceFiscale) throws RemoteException {
        //se il valore è 'true' allora esiste un cittadino con questo ID Vaccinazione
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" where idvacc = ? and fiscale = ?");
            statement.setLong(1, IDvacc);
            statement.setString(2,CodiceFiscale);
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
    //controllo che il Cittadino che si vuole registrare non sia gia presente nel DB Cittadini
    @Override
    public boolean controllaCittadinoEsistenza(String CodiceFiscale) throws RemoteException {
        //se il valore è 'true' allora esiste gia un cittadino registrato
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Cittadino\" where codicefiscale = ?");
            statement.setString(1,CodiceFiscale);
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
    public Cittadino controllaCittadinoLogin(String userid, String password, int numCittadino) throws RemoteException {
        //se il valore è 'true' allora esiste un cittadino con questo User ID
        Cittadino cittadino = null;
        try {
            PreparedStatement statement = DB.prepareStatement("select * from \"Cittadino\" where userid = ? and password = ?");
            statement.setString(1, userid);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int i = 1;
                cittadino = new Cittadino();
                cittadino.setIdVacc(resultSet.getLong(i++));
                cittadino.setNome(resultSet.getString(i++));
                cittadino.setCognome(resultSet.getString(i++));
                cittadino.setCodiceFiscale(resultSet.getString(i++));
                cittadino.setEmail(resultSet.getString(i));
                cittadino.setUserid(userid);
                cittadino.setPassword(password);
            }
            if (cittadino != null) {
                System.out.println("Cittadino ("+ numCittadino+") effettua login come: "+cittadino.getCodiceFiscale());
                cittadino = controllaCittadinoDose(cittadino);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cittadino;
    }
    @Override
    public Cittadino controllaCittadinoDose(Cittadino cittadino) throws RemoteException {
        try {
            PreparedStatement statement = DB.prepareStatement("select Max(idvacc) from \"Vaccinati\"" +
                    "where fiscale LIKE ?" +
                    "Group by fiscale");
            statement.setString(1, cittadino.getCodiceFiscale());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long Idvacc_new = resultSet.getLong(1);
                if(cittadino.getIdVacc() != Idvacc_new){
                    cittadino.setIdVacc(Idvacc_new);
                    statement = DB.prepareStatement("update \"Cittadino\" set idvacc = ? where codicefiscale LIKE ? ");
                    statement.setLong(1, cittadino.getIdVacc());
                    statement.setString(2, cittadino.getCodiceFiscale());
                    statement.executeQuery();
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cittadino;
    }

    //metodo che restituisce il numero per il Cittadino che si è appena connesso e non ha ancora fatto il login
    @Override
    public int getCountC() throws RemoteException {
        if(!Cdisconnessi.isEmpty()){
            int num = Cdisconnessi.remove(0);
            Cconnessi.add(num-1,num);
            return num;
        }
        countC++;
        Cconnessi.add(countC-1,countC);
        System.out.println("Cittadino (" +countC+ "): connesso");
        return countC;
    }
    //metodo permette di togliere il numero dal Cittadino che si è appena scollegato
    @Override
    public void diminuisciCountC(int id) throws RemoteException {
        Cdisconnessi.add(Cconnessi.remove(id-1));
        System.out.println("Cittadino (" +countC+ "): disconnesso");
    }

    @Override
    public void logoutCittadino(String codiceFiscale, int countcittadino) {
        System.out.println(codiceFiscale+ " effettua logout e torna Cittadino("+countcittadino+")");
    }

    //Evento Avverso
    //Registrazione Evento Avverso
    //DA CONTROLLARE I SYSTEM.OUT E ALTRE COSE
    @Override
    public String inserisciEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException {
        //con una query un cittadino inserisce un evento avverso
        String SQL = "INSERT INTO \"EventoAvverso\"(idCentro,codiceFiscale,evento,severita,note) VALUES(?,?,?,?,?)";
        try {
            System.out.println(eventoAvverso.getCodiceFiscale() + " inserimento "+ eventoAvverso);
            PreparedStatement pstmt = DB.prepareStatement(SQL);
            pstmt.setInt(1, eventoAvverso.getIdCentro());
            pstmt.setString(2, eventoAvverso.getCodiceFiscale());
            pstmt.setString(3, eventoAvverso.getEvento());
            pstmt.setInt(4, eventoAvverso.getSeverita());
            pstmt.setString(5, eventoAvverso.getNoteOpz());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            if(e.getMessage().contains("cKey_EA")){
                System.out.println(eventoAvverso.getCodiceFiscale() + " inserimento Evento non avvenuto con successo, DUPLICATO");
                return "Evento già registrato !";
            }
            System.out.println(eventoAvverso.getCodiceFiscale() + " inserimento Evento non avvenuto con successo, "+e.getMessage());
            return "Errore di connessione con il DB !";
        }
        System.out.println(eventoAvverso.getCodiceFiscale() + " inserimento Evento avvenuto con successo");
        return "";
    }

    //Controllo sull'evento avverso della persona che lo aggiunge
    @Override
    public boolean controllaEventoAvverso(EventoAvverso eventoAvverso) throws RemoteException {
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"EventoAvverso\"" +
                    "where codicefiscale = ? and data = ? and evento = ?");
            statement.setString(1, eventoAvverso.getCodiceFiscale());
            statement.setString(2, eventoAvverso.getData());
            statement.setString(3, eventoAvverso.getEvento());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int val = resultSet.getInt(1);
                if(val >= 1){
                    return true;
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CentroVaccinale cercaCentroVaccinale_CF(String CodiceFiscale) throws RemoteException {
        //Usando una query ricerchiamo dentro la tabella CentroVaccinale join con Vaccinati il nome del centro
        //che il Cittadino con il codice fiscale inserito nel metodo ha fatto l'ultimo vaccino

        ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("select cv.id , cv.nome from \"CentriVaccinali\" as cv, \"Vaccinati\" as v where lower(v.fiscale) like ? and cv.id = v.centro");//creare una query giusta per il trovare l'id del centro
            statement.setString(1, "%" + CodiceFiscale.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int ID = resultSet.getInt("id");
                String centro = resultSet.getString("nome");
                CentroVaccinale cv = new CentroVaccinale(ID, centro);
                arrayListCentri.add(cv);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListCentri.get(0);
    }

    @Override
    public ArrayList<Vaccinato> cercaVaccinato(String codiceFiscale) throws RemoteException{
        //Usando una query ricerchiamo dentro la tabella Vaccinato il codicefiscale del vaccinato

        ArrayList<Vaccinato> arrayListVaccinati = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM \"CentriVaccinali\" WHERE fiscale LIKE ? ");
            statement.setString(1, "%" + codiceFiscale + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String fiscale = resultSet.getString("fiscale");
                int centro = resultSet.getInt("centro");
                String giorno = resultSet.getString("giorno");
                String vaccino = resultSet.getString("vaccino");
                int dose = resultSet.getInt("dose");
                Vaccinato cv = new Vaccinato(nome, cognome, centro, ID, fiscale, giorno, vaccino,dose);
                arrayListVaccinati.add(cv);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayListVaccinati;
    }

    @Override
    public ArrayList<EventoAvverso> visualizzaInfoCentroVaccinale(int chiavePrimariaCentri) throws RemoteException {

        ArrayList<EventoAvverso> eventoAvversoArrayList = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT LOWER(evento) AS tipologia, COUNT(severita) AS totale, AVG(severita) AS media FROM \"EventoAvverso\" WHERE idcentro = ? GROUP BY tipologia");
            statement.setInt(1, chiavePrimariaCentri);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String evento = resultSet.getString("tipologia");
                int segnalazioni = resultSet.getInt("totale");
                double media = resultSet.getDouble("media");

                EventoAvverso eventoAvverso = new EventoAvverso(evento, segnalazioni, media);
                eventoAvversoArrayList.add(eventoAvverso);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return eventoAvversoArrayList;

        /*ArrayList<CentroVaccinale> arrayListCentri = new ArrayList<>();

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM \"CentriVaccinali\" WHERE lower(nome) LIKE ? ORDER BY lower(nome)");
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
        return arrayListCentri;*/
    }

    //LUCA E' TUO

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere
    @Override
    public boolean controlloVaccCitt(Cittadino cittadino) throws RemoteException{
        boolean esitoControllo = false;
        try {
            PreparedStatement statement = DB.prepareStatement("select count(*) from \"Vaccinati\" JOIN \"Cittadino\" where lower(idvacc) = lower(idvacc)");
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
    public boolean controllaConnessione() {
        try {
            PreparedStatement statement = DB.prepareStatement("select MAX(dose) from \"Vaccinati\"");
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private void connessione_server(){
        Registry registro = null;
        try {
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("CentroVaccinale", this);
            System.out.println("Server pronto!!!");
        } catch (RemoteException e) {
            System.err.println("Errore nella creazione del registro del Centro Vaccinale:\n"+e.getMessage());
            try {
                if(!UnicastRemoteObject.unexportObject(registro, false)){
                    System.out.println("IL REGISTRO E' GIA' IN USO!!!");
                }
            } catch (NoSuchObjectException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    private void connessione_DB(){
        try {
            DB = DriverManager.getConnection(url_DB,user_DB,password_DB);
            if(DB != null){
                System.out.println("Connessione al DB completata!!!");
            }else{
                System.out.println("Connessione al DB fallita!!!");
                System.out.println("Chiusura Applicazione!!!");
                System.exit(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Chiusura Applicazione!");
            System.exit(-1);
        }
    }
    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (server != null) {
            server.connessione_server();
            server.connessione_DB();
        }
    }
}