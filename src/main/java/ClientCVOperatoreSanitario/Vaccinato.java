package ClientCVOperatoreSanitario;

import java.io.Serializable;

/**
 * La classe vaccinato definisce tutti i campi necessari alla creazione e alla fruizione
 * di un vaccinato.
 * @author Pietro
 * @version 1.0
 */
public class Vaccinato implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private int centroVacc;
    private long idVacc;
    private String codiceFisc;
    private String data;
    private String vaccino;
    private int dose;

    /**
     * Il costruttore di un vaccinato.
     * @param nome indica il nome di un vaccinato.
     * @param cognome indica il cognome di un vaccinato.
     * @param centroVacc indica il centro vaccinale presso cui è stata ricevuta la vaccinazione.
     * @param idVacc indica l'ID della vaccinazione ricevuta.
     * @param codiceFisc indica il codice fiscale del vaccinato.
     * @param data indica la data della somministrazione del vaccino.
     * @param vaccino indica il tipo di vaccino somministrato.
     * @param dose indica il numero della dose somministrata.
     */
    public Vaccinato( String nome, String cognome, int centroVacc, long idVacc, String codiceFisc, String data, String vaccino, int dose) {
        this.nome = nome;
        this.cognome = cognome;
        this.centroVacc = centroVacc;
        this.idVacc = idVacc;
        this.codiceFisc = codiceFisc;
        this.data = data;
        this.vaccino = vaccino;
        this.dose = dose;
    }

    /**
     * Il metodo restituisce il nome del vaccinato.
     * @return il nome del centro vaccinale
     */
    public String getNome() {
        return nome;
    }

    /**
     * Il metodo permette di definire un nuovo nome del vaccinato.
     * @param nome indica il nuovo nome del vaccinato.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Il metodo resitutisce il cognome del vaccinato.
     * @return resitutisce il cognome del vaccinato.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Il metodo permette di definire un nuovo cognome del vaccinato.
     * @param cognome indica il nuovo cognome del vaccinato.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Il metodo restituisce il centro vaccinale presso cui è stata ricevuta la vaccinazione.
     * @return il centro vaccinale presso cui è stata ricevuta la vaccinazione.
     */
    public int getCentroVacc() {
        return centroVacc;
    }

    /**
     * Il metodo permette di definire un nuovo centro vaccinale.
     * @param centroVacc indica il nuovo centro vaccinale.
     */
    public void setCentroVacc(int centroVacc) {
        this.centroVacc = centroVacc;
    }

    /**
     * Il metodo restituisce l'ID della vaccinazione.
     * @return l'ID della vaccinazione.
     */
    public long getIdVacc() {
        return idVacc;
    }

    /**
     * Il metodo permette di definire un nuovo ID di vaccinazione.
     * @param idVacc indica il nuovo ID della vaccinazione
     */
    public void setIdVacc(long idVacc) {
        this.idVacc = idVacc;
    }

    /**
     * Il metodo restituisce il codice fiscale del vaccinato.
     * @return il codice fiscale del vaccinato.
     */
    public String getCodiceFisc() {
        return codiceFisc;
    }

    /**
     * IL metodo permette di definire il nuovo codice fiscale del vaccinato.
     * @param codiceFisc indica il nuovo codice fiscale del vaccinato.
     */
    public void setCodiceFisc(String codiceFisc) {
        this.codiceFisc = codiceFisc;
    }

    /**
     * Il metodo restituisce la data della vaccinazione.
     * @return la data della vaccinazione.
     */
    public String getData() {
        return data;
    }

    /**
     * Il metodo permette di definire la nuova data di avvenuta vaccinazione.
     * @param data indica la nuova data di avvenuta vaccinazione.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Il metodo restituisce il tipo di vaccino somministrato.
     * @return il tipo di vaccino somministrato.
     */
    public String getVaccino() {
        return vaccino;
    }

    /**
     * Il metodo permette di definire la tipologia di vaccino somministrato.
     * @param vaccino indica la tipologia di vaccino somministrato.
     */
    public void setVaccino(String vaccino) {
        this.vaccino = vaccino;
    }

    /**
     * Il metodo restituisce il numero della dose somministrata.
     * @return il numero della dose somministrata.
     */
    public int getDose() {
        return dose;
    }

    /**
     * Il metodo permette di definire il nuovo numero di dosi somministrate.
     * @param dose indica il nuovo numero di dosi somministrate.
     */
    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Nome e Cognome: " + nome + " " + cognome + ", Codice Fiscale: " + codiceFisc + ", ID Centro: " + centroVacc + ", Tipo: " + vaccino + ", N° dose: " + dose + ", ID Vaccinazione: " + idVacc + ", Data: " + data;
    }
}