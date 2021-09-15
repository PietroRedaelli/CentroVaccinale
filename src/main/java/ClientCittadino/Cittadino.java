package ClientCittadino;

import ServerPackage.ServerInterface;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cittadino implements Serializable {

    private static final long serialVersionUID = 1L;

    String nome;
    String cognome;
    String codiceFiscale;
    String email;
    String userid;
    String passowrd;
    String idVacc=null;

    public Cittadino(String nome, String cognome, String codiceFiscale, String email, String userid, String passowrd) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userid = userid;
        this.passowrd = passowrd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getIdVacc() {
        return idVacc;
    }

    public void setIdVacc(String idVacc) {
        this.idVacc = idVacc;
    }

    public static void main(String[] args){
        try {
            Registry registro = LocateRegistry.getRegistry(1099);
            ServerInterface server = (ServerInterface) registro.lookup("CentroVaccinale");
        } catch (Exception e) {
            System.err.println("Client: errore di connessione al server \n" + e.getMessage());
            System.exit(0);
        }
    }
}
