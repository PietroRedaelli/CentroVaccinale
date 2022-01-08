package ClientCittadino;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventoAvverso implements Serializable {

    private static final long serialVersionUID = 1L;

    int idCentro;
    String nomeCentro;
    long idVacc;
    String CodiceFiscale;
    String evento;
    int severita;
    String data;
    String noteOpz;


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

    @Override
    public String toString() {
            return "Evento Avverso:" +
                    " Tipo: " + evento +
                    ", Severita: " + severita +
                    ", Note: " + noteOpz;
    }
}
