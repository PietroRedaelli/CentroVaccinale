package ServerCV;

import ClientCVCittadino.Cittadino;
import ClientCVCittadino.EventoAvverso;
import ClientCVOperatoreSanitario.CentroVaccinale;
import ClientCVOperatoreSanitario.Vaccinato;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * La classe Server ha lo scopo di gestire le connessioni con i ClientCittadino e
 * i ClientOperatoreSanitario ed effettuare le operazioni sul database.
 * @author Pietro
 * @version 1.0
 */
public class Server extends UnicastRemoteObject implements ServerInterface{

    private static final long serialVersionUID = 1L;

    @FXML private TextField TFUser;
    @FXML private TextField TFPassword;
    @FXML private TextField TFHost;
    @FXML private Label LBConnessione;
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;
    @FXML private TextField TFCentri;
    @FXML private TextField TFPrima;
    @FXML private TextField TFSeconda;
    @FXML private TextField TFTerza;
    @FXML private TextField TFEventi;

    private static String user;
    private static String password;
    protected String url_DB = "jdbc:postgresql://localhost:5432/LabB";
    protected String user_DB = "postgres";
    protected String password_DB = "Xbox360Live";

    private int numeroCentriVaccinali;
    private int numeroVaccinati1;
    private int numeroVaccinati2;
    private int numeroVaccinati3;
    private int numeroEventi;

    protected static Connection DB = null;
    protected static Registry registro = null;

    public Server() throws RemoteException {
        super();
    }

    /**
     * Il metodo si occupa dell'instaurazione delle connessioni. Tramite oggetto remoto
     * si effettua l'inizializzazione della connessione col Database.
     * Si acquisiscono le credenziali del Database e successivamente si instaurano le connessioni
     * necessarie al funzionamento dei client.
     */
    public void lancioServerEDatabase() {
        user = TFUser.getText().trim();
        password = TFPassword.getText().trim();
        String host = TFHost.getText().trim();

        if (!user.equals(user_DB) || !password.equals(password_DB) || !host.equals("localhost")) {
            LBConnessione.setVisible(true);
            return;
        } else {
            LBConnessione.setVisible(false);
        }

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
        pane1.setVisible(false);
        pane2.setVisible(true);

        Runnable helloRunnable = new Runnable() {
            public void run() {
                acquisisciInfo();
                mostraInfo();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Il metodo ha lo scopo di permettere la connessione al server.
     */
    private void connessione_server(){
        try {
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("CentroVaccinale", this);
            System.out.println("Server pronto!");
        } catch (RemoteException e) {
            System.err.println("Errore nella creazione del registro del Centro Vaccinale:\n"+e.getMessage());
            try {
                if(!UnicastRemoteObject.unexportObject(registro, false)){
                    System.out.println("IL REGISTRO E' GIA' IN USO!");
                }
            } catch (NoSuchObjectException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    /**
     * Il metodo ha lo scopo di effettuare la connessione al databse.
     */
    private void connessione_DB(){
        try {
            DB = DriverManager.getConnection(url_DB,user,password);
            if(DB != null){
                System.out.println("Connessione al DB completata!");
            }else{
                System.out.println("Connessione al DB fallita!");
                System.out.println("Chiusura Applicazione!");
                System.exit(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Chiusura Applicazione!");
            System.exit(-1);
        }
    }

    /**
     * Il metodo ha la funzione di disconnettere correttamente il server
     * e il Database quando si chiude la finestra del Server
     */
    public static void disconnetti() {
        try {
            DB.close();
            System.out.println("Chiusura Database!");
        } catch (SQLException | NullPointerException e) {
            //e.printStackTrace();
            System.out.println("Il DB non era stato inizializzato");
        }
        try {
            registro.unbind("CentroVaccinale");
            UnicastRemoteObject.unexportObject(registro, true);
            System.out.println("Chiusura Server!");
        } catch (RemoteException | NotBoundException | NullPointerException e) {
            //e.printStackTrace();
            System.out.println("Il Server non era stato inizializzato");
        }
    }

    private void acquisisciInfo() {
        String centri = "SELECT COUNT(*) FROM \"CentriVaccinali\"";
        String vaccinati1 = "SELECT COUNT(*) FROM \"Vaccinati\" WHERE dose = 1";
        String vaccinati2 = "SELECT COUNT(*) FROM \"Vaccinati\" WHERE dose = 2";
        String vaccinati3 = "SELECT COUNT(*) FROM \"Vaccinati\" WHERE dose = 3";
        String eventiAvversi = "SELECT COUNT(*) FROM \"EventoAvverso\"";
        try {
            PreparedStatement stm = DB.prepareStatement(centri);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                numeroCentriVaccinali = rs.getInt(1);

            PreparedStatement stm1 = DB.prepareStatement(vaccinati1);
            ResultSet rs1 = stm1.executeQuery();
            if (rs1.next())
                numeroVaccinati1 = rs1.getInt(1);

            PreparedStatement stm2 = DB.prepareStatement(vaccinati2);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next())
                numeroVaccinati2 = rs2.getInt(1);

            PreparedStatement stm3 = DB.prepareStatement(vaccinati3);
            ResultSet rs3 = stm3.executeQuery();
            if (rs3.next())
                numeroVaccinati3 = rs3.getInt(1);

            PreparedStatement stm4 = DB.prepareStatement(eventiAvversi);
            ResultSet rs4 = stm4.executeQuery();
            if (rs4.next())
                numeroEventi = rs4.getInt(1);
        } catch (SQLException e) {
            System.out.println("Errore Connessione metodo \"acquisisciInfo\"");
        }
    }

    private void mostraInfo(){
        TFCentri.setText(String.valueOf(numeroCentriVaccinali));
        TFPrima.setText(String.valueOf(numeroVaccinati1));
        TFSeconda.setText(String.valueOf(numeroVaccinati2));
        TFTerza.setText(String.valueOf(numeroVaccinati3));
        TFEventi.setText(String.valueOf(numeroEventi));
    }

    /**
     * Il metodo si occupa della registrazione di nuovo un centro vaccinale nel database.
     * @param centroVaccinale indica il centro vaccinale che si vuole registrare nel databse.
     * @throws RemoteException
     */
    @Override
    public void registraCentroVaccinale(CentroVaccinale centroVaccinale) throws RemoteException {
        String SQL = "INSERT INTO \"CentriVaccinali\"(nome, comune, indirizzo, civico, sigla, cap, tipologia) VALUES(?,?,?,?,?,?,?)";
        try {
            System.out.println("Registrazione CentroVaccinale: \n" + centroVaccinale);
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
            System.out.println("Registrazione Centro Vaccinale avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Il metodo ha lo scopo di ricercare un centro vaccinale tramite nome nel database.
     * @param nomeCentro indica il nome del centro che si vuole cercare nel database.
     * @return una lista dei centri vaccinali aventi come nome quello passato come parametro.
     * @throws RemoteException
     */
    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String nomeCentro) throws RemoteException {

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

    /**
     * Il metodo ha lo scopo di ricercare un centro vaccinale per comune e tipologia nel database.
     * @param comuneInserito indica il comune del centro vaccinale da ricercare.
     * @param tipologia indica la tipologia del centro vaccinale da ricercare.
     * @return una lista dei centri vaccinali che soddisfano le condizioni specificate.
     * @throws RemoteException
     */
    @Override
    public ArrayList<CentroVaccinale> cercaCentroVaccinale(String comuneInserito, String tipologia) throws RemoteException {

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

    /**
     * Il metodo ha lo scopo di controllare che i paramentri dell centro vaccinale
     * che si vuole registrare siano corretti.
     * @param cv indica il centro vaccinale su cui si vuole effettuare il cotrollo.
     * @return una stringa contente l'esito del controllo.
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di registrare un vaccinato nella tabella Vaccinati nel database.
     * @param vaccinato indica il vaccinato che si vuole registrare nella tabella Vaccinati del databse.
     * @throws RemoteException
     */
    @Override
    public void registraVaccinato(Vaccinato vaccinato) throws RemoteException {

        String SQL = "INSERT INTO \"Vaccinati\"(idvacc, nome, cognome, fiscale, centro, giorno, vaccino, dose) VALUES(?,?,?,?,?,?,?,?)";
        try {
            System.out.println("Registrazione Vaccinato: \n" + vaccinato);
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
            System.out.println("Registrazione Vaccinato avvenuta con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Il metodo ha lo scopo di controllare che i campi inseriti del vaccinato che si vuole
     *  registrare siano corretti.
     * @param vacc indica il vaccinato per cui si vogliono controllare i campi inseriti
     * @return una stringa contenente l'esito del controllo.
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di registrare un cittadino nella tabella Cittadino del databse.
     * @param cittadino indica il cittadino che si vuole registrare nella tabella Cittadino del database.
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di controllare che esista il cittadino nella tabella Vaccinati.
     * @param cittadino indica il cittadino su cui si vuole effettuare il controllo.
     * @return true se il cittadino esiste già nella tabella Vaccinati, false altrimenti.
     * @throws RemoteException
     */
    @Override
    public boolean controllaCittadinoDatiPersonali(Cittadino cittadino) throws RemoteException {
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

    /**
     * Il metodo ha lo scopo di controllare che non esista già un cittadino con la stessa email.
     * @param email indica l'email del cittadino che si vuole controllare.
     * @return true se esiste già un cittadino con l'email in questione, false altrimenti.
     * @throws RemoteException
     */
    @Override
    public boolean controllaCittadinoEmail(String email) throws RemoteException {
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

    /**
     * Il metodo ha lo scopo di controllare che non esista già un cittadino con lo stesso userID.
     * @param UserID indica lo userID del cittadino.
     * @return true se esiste già un cittadino con l'userID in questione, false altrimenti.
     * @throws RemoteException
     */
    @Override
    public boolean controllaCittadinoUserId(String UserID) throws RemoteException {
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

    /**
     * Il metodo ha lo scopo di controllare che l'ID vaccinazione sia corretto.
     * @param IDvacc indica l'ID della vaccinazione.
     * @param CodiceFiscale indica il codice fiscale del cittadino.
     * @return true se esiste già un cittadino con questi valori, false altrimenti.
     * @throws RemoteException
     */
    @Override
    public boolean controllaCittadinoIDvacc(long IDvacc, String CodiceFiscale) throws RemoteException {
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

    /**
     * Il metodo ha lo scopo di controllare che il cittadino che si sta registrando non sia già
     * presente nella tabella Cittadino del database.
     * @param CodiceFiscale indica il codice fiscale del cittadino.
     * @return true se il cittadino esiste già nella tabella Cittadino, false altrimenti
     * @throws RemoteException
     */
    @Override
    public boolean controllaCittadinoEsistenza(String CodiceFiscale) throws RemoteException {
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

    /**
     * Il metodo ha lo scopo di controllare che i campi inseriti dal cittadino in fase di login siano corretti.
     * @param userid indica l'userID dell'account del cittadino.
     * @param password indica la password dell'account del cittadino.
     * @return il cittadino aventi come userID e password quelli passati come paramentri.
     * @throws RemoteException
     */
    @Override
    public Cittadino controllaCittadinoLogin(String userid, String password) throws RemoteException {
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
                System.out.println("Cittadino effettua login come: "+cittadino.getCodiceFiscale());
                cittadino = controllaCittadinoDose(cittadino);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cittadino;
    }

    /**
     * Il meotodo viene utilizzato dopo l'accettazione del login per restituire la variabile
     * cittadino con i dati più aggiornati risalenti all'ultima vaccinazione.
     * @param cittadino il nome del cittadino di cui si vuole controllare la dose.
     * @return il cittadino con i dati relativi all'ultima vaccinazione.
     * @throws RemoteException
     */
    @Override
    public Cittadino controllaCittadinoDose(Cittadino cittadino) throws RemoteException {
        try {
            PreparedStatement statement = DB.prepareStatement("select Max(idvacc) from \"Vaccinati\"" +
                    "where fiscale LIKE ?" +
                    "Group by fiscale");
            statement.setString(1, cittadino.getCodiceFiscale());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long Idvacc_new = resultSet.getLong(1);
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

    /**
     * Il metodo permette di inserire un nuovo evento avverso nella tabella EventoAvverso del database.
     * @param eventoAvverso indica l'evento avverso che si vuole aggiungere.
     * @return una stringa che indica il risultato dell'operazione.
     * @throws RemoteException
     */
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

    /**
     * Il metodo controlla che l'evento avverso inserito non sia già stato aggiunto nel giorno corrente.
     * @param eventoAvverso indica l'evento avverso inserito che si vuole controllare.
     * @return true se l'evento è già presente, false altrimenti.
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di cercare un centro vaccinale presso cui è stato vaccinato un cittadino
     * avente come codice fiscale quello passato come argomento.
     * @param CodiceFiscale indica il codice fiscale su cui si vuole effettuare la ricerca.
     * @return il centro vaccinale presso cui è stato vaccinato un cittadino avente il codice fiscale
     * passato come argomento
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di cercare un Vaccinato tramite codice fiscale nella tabella Vaccinati del databse.
     * @param codiceFiscale indica il codice fiscale del vaccinato che si vuole cercare.
     * @return una lista di vaccinati aventi come codice fiscale il codice fiscale passato come argomento.
     * @throws RemoteException
     */
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

    /**
     * Il metodo ha lo scopo di permette la visualizzazione degli eventi avversi verificati
     * in seguito alle vaccinazioni in un centro vaccinale.
     * @param chiavePrimariaCentri indica la chiave primaria dei centri vacciali.
     * @return una lista degli eventi avversi verificati in seguito alle vaccinazioni in un certo
     * centro vaccinale.
     * @throws RemoteException
     */
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
    }

    //PIETRO: controlla che esista la persona nella tabella dei vaccinati: cod fisc e idvacc devono corrispondere

    /**
     * Il metodo ha lo scopo di controllare che esista il cittadino passato come argomento
     * nella tabella vaccinati del database. In questo modo si saprà se un cittadino è stato
     * vaccinato oppure no.
     * @param cittadino indica il cittadino su cui si vuole effettuare il controllo.
     * @return true se esiste una corrispondenza tra un cittadino e un vaccinato, false altrimenti.
     * @throws RemoteException
     */
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
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}