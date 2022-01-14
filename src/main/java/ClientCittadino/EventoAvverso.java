package ClientCittadino;

import org.controlsfx.control.Rating;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Rating media;
    private double intMedia;


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

    public EventoAvverso(String evento, int severita, double intMedia) {
        this.evento = evento;
        this.severita = severita;
        //this.media = new Rating(intMedia);
        this.intMedia = intMedia;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public long getIdVacc() {
        return idVacc;
    }

    public void setIdCentro(long idVacc) {
        this.idVacc = idVacc;
    }

    public String getCodiceFiscale() {
        return CodiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        CodiceFiscale = codiceFiscale;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public int getSeverita() {
        return severita;
    }

    public void setSeverita(int severita) {
        this.severita = severita;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNoteOpz() {
        return noteOpz;
    }

    public void setNoteOpz(String noteOpz) {
        this.noteOpz = noteOpz;
    }

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
