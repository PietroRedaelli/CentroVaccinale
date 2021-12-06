package ClientCittadino;

import java.io.Serializable;

public class Cittadino implements Serializable {

    private static final long serialVersionUID = 1L;

    String nome;
    String cognome;
    String codiceFiscale;
    String email;
    String userid;
    String password;
    String idVacc=null;

    public Cittadino(){}
    public Cittadino(String nome, String cognome, String codiceFiscale, String email, String userid, String password, String idVacc) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.idVacc = null;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdVacc() {
        return idVacc;
    }

    public void setIdVacc(String idVacc) {
        this.idVacc = idVacc;
    }
}
