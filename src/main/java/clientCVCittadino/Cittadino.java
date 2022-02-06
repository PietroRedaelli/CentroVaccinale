package clientCVCittadino;

import java.io.Serializable;

/**
 * La classe Cittadino rappresenta tutti gli attributi di un cittadino ai fini della
 * fruizione delle funzionalità di un centro vaccinale a lui dedicate
 * @author Pietro
 * @version 1.0
 */

public class Cittadino implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private  String userid;
    private String password;
    private long idVacc = 0;

    public Cittadino(){}

    /**
     *
     * @param nome nome di uno cittadino.
     * @param cognome cognome di un cittadino.
     * @param codiceFiscale codiceFiscale di un cittadino.
     * @param email email di un cittadino.
     * @param userid userid di un cittadino.
     * @param password password di un cittadino.
     * @param idVacc idVacc di un cittadino.
     */
    public Cittadino(String nome, String cognome, String codiceFiscale, String email, String userid, String password, long idVacc) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.idVacc = idVacc;
    }

    /**
     * Il metodo restituisce il nome del cittadino.
     * @return il nome del cittadino.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Il metodo permette di modificare il nome del cittadino.
     * @param nome indica il nuovo nome del cittadino.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Il metodo restituisce il cognome del cittadino.
     * @return il cognome del cittadino.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Il metodo permette di modificare il cognome del cittadino.
     * @param cognome indica il nuovo cognome del cittadino.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Il metodo restituisce il codice fiscale del cittadino.
     * @return il codice fiscale del cittadino.
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Il metodo permette di modificare il codice fiscale del cittadino.
     * @param codiceFiscale indica il nuovo codice fiscale del cittadino.
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Il metodo restituisce l'email del cittadino.
     * @return l'email del cittadino.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Il metodo permette di modificare l'email del cittadino.
     * @param email indica la nuova email del cittadino.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Il metodo restituisce l'userID del cittadino.
     * @return l'userID del cittadino.
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Il metodo permette di modificare lo userID del cittadino.
     * @param userid indica il nuovo userID del cittadino.
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Il metodo restituisce la password del cittadino.
     * @return la password del cittadino.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Il metodo permette di modificare la password del cittadino.
     * @param password indica la nuova password del cittadino.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Il metodo restituisce l'ID della vaccinazione effettuata dal cittadino.
     * @return l'ID dela vaccinazione effettuata dal cittadino.
     */
    public long getIdVacc() {
        return idVacc;
    }

    /**
     * Il metodo permette di modificare l'ID della vaccinazione effettuata dal cittadino.
     * @param idVacc indica il nuovo ID della vaccinazione effettuata dal cittadino.
     */
    public void setIdVacc(long idVacc) {
        this.idVacc = idVacc;
    }

    @Override
    public String toString() {
        return  "Nome e Cognome: " + nome + " " + cognome + ", Codice Fiscale: " + codiceFiscale + ", Email: " + email + ", Userid: " + userid + ", Password: " + password + ", ID Vaccinazione: " + idVacc ;
    }
}
