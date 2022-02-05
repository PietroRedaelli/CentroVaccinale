package ClientCittadino;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * La classe EventoAvverso definisce un evento avverso secondo tutti i suoi attributi.
 * @author Pietro
 * @version 1.0
 */
public class EventoAvverso implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idCentro;
    private String nomeCentro;
    private long idVacc;
    private String CodiceFiscale;
    private String evento;
    private int severita;
    private String data;
    private String noteOpz;
    private double intMedia;

    /**
     * Costruttore di un evento avverso.
     * @param idCentro corrisponde all'ID del centro vaccinale presso cui ci si è vaccinati.
     * @param nomeCentro corrisponde al nome del centro vaccinale presso cui ci si è vaccinati.
     * @param idVacc corrisponde all'ID del vaccino ricevuto.
     * @param CodiceFiscale corrisponde al codice fiscale del cittadino di cui si sta segnalando un evento avverso.
     * @param evento corrisponde al tipo dell'evento avverso.
     * @param severita indica numericamente la severtà dell'evento avverso (da 1 a 5).
     * @param noteOpz note opzionali inseribili dal cittadino vaccinato.
     */
    public EventoAvverso(int idCentro, String nomeCentro, long idVacc, String CodiceFiscale, String evento, int severita, String noteOpz) {
        this.idCentro = idCentro;
        this.nomeCentro = nomeCentro;
        this.idVacc = idVacc;
        this.CodiceFiscale = CodiceFiscale;
        this.evento = evento;
        this.severita = severita;
        this.data = LocalDate.now().toString();
        this.noteOpz = noteOpz;
    }

    /**
     *
     * @param evento corrisponde al tipo dell'evento avverso.
     * @param severita indica numericamente la severtà dell'evento avverso (da 1 a 5).
     */
    public EventoAvverso(String evento, int severita, double intMedia) {
        this.evento = evento;
        this.severita = severita;
        //this.media = new Rating(intMedia);
        this.intMedia = intMedia;
    }

    /**
     * Il metodo restituisce l'ID del centro vaccinale presso cui si è stati vaccinati.
     * @return l'ID del centro vaccinale presso cui si è stati vaccinati.
     */
    public int getIdCentro() {
        return idCentro;
    }

    /**
     * Il metodo permette d'inserire un nuovo ID di un centro vaccinale.
     * @param idCentro corrisponde a un nuovo ID di un centro vaccinale..
     */
    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    /**
     * Il metodo restituisce il nome del centro vaccinale presso cui si è stati vaccinati.
     * @return nome del centro vaccinale presso cui si è stati vaccinati.
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * Il metodo permette d'inserire un nuovo nome di un centro vaccinale.
     * @param nomeCentro corrisponde a un nuovo nome di un centro vaccinale.
     */
    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    /**
     * Il metodo restituisce l'ID della vaccinazione ricevuta.
     * @return l'ID della vaccinazione ricevuta.
     */
    public long getIdVacc() {
        return idVacc;
    }

    /**
     * Il metodo permette d'inserire un nuovo ID di un centro vaccinale.
     * @param idVacc corrisponde a un nuovo ID di un centro vaccinale.
     */
    public void setIdCentro(long idVacc) {
        this.idVacc = idVacc;
    }

    /**
     * Il metodo restituisce il codice fiscale della persona di cui si sta segnalando un evento avverso.
     * @return codice fiscale della persona di cui si sta segnalando un evento avverso.
     */
    public String getCodiceFiscale() {
        return CodiceFiscale;
    }

    /**
     * Il metodo permette d'inserire un nuovo codice fiscale per il cittadino vaccinato per cui
     * si è verificato l'evento avverso in questione.
     * @param codiceFiscale corrisponde a un nuovo codice fiscale per il cittadino vaccinato.
     */
    public void setCodiceFiscale(String codiceFiscale) {
        CodiceFiscale = codiceFiscale;
    }

    /**
     * Il metodo restituisce il tipo di evento.
     * @return il tipo di evento.
     */
    public String getEvento() {
        return evento;
    }

    /**
     * Il metodo permette d'inserire un nuovo tipo di evento.
     * @param evento corrisponde a un nuovo tipo di evento.
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * Il metodo restituisce un numero indicante la severità dell'evento avverso.
     * @return un numero indicante la severità dell'evento avverso.
     */
    public int getSeverita() {
        return severita;
    }

    /**
     * Il metodo permette d'inserire una nuova severità (un numero da 1 a 5).
     * @param severita corrisponde a una nuova severità (un numero intero da 1 a 5).
     */
    public void setSeverita(int severita) {
        this.severita = severita;
    }

    /**
     * Il metodo restituisce la data relativa ad un certo evento avverso.
     * @return la data relativa ad un certo evento avverso.
     */
    public String getData() {
        return data;
    }

    /**
     * Il metodo permette d'inserire una nuova data in cui è stato rilevato un nuovo evento avverso.
     * @param data corrisponde a una nuova data relativa ad un evento avverso.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Il metodo restituisce le note opzionali.
     * @return le note opzionali.
     */
    public String getNoteOpz() {
        return noteOpz;
    }

    /**
     * Il metodo permette d'inserire delle nuove note opzionali relative all'evento avverso.
     * @param noteOpz corrisponde alle nuove note opzionali.
     */
    public void setNoteOpz(String noteOpz) {
        this.noteOpz = noteOpz;
    }

    /**
     * Il metodo permette d'inserire un nuovo ID relativo a una vaccinazione effettuata.
     * @param idVacc corrisponde a un nuovo ID relativo a una vaccinazione effettuata.
     */
    public void setIdVacc(long idVacc) {
        this.idVacc = idVacc;
    }

    public double getIntMedia() {
        return intMedia;
    }

    public void setIntMedia(int intMedia) {
        this.intMedia = intMedia;
    }

    @Override
    public String toString() {
            return "Evento Avverso:" +
                    " Tipo: " + evento +
                    ", Severita: " + severita +
                    ", Note: " + noteOpz;
    }
}