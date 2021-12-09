package ClientCittadino;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventoAvverso implements Serializable {

    private static final long serialVersionUID = 1L;

    String idCentro;
    String CodiceFiscale;
    String evento;
    int severita;
    String data;
    String noteOpz;


    public EventoAvverso(String idCentro, String CodiceFiscale, String evento, int severita, String noteOpz) {
        this.idCentro = idCentro;
        this.CodiceFiscale = CodiceFiscale;
        this.evento = evento;
        this.severita = severita;
        this.noteOpz = noteOpz;
    }

    public EventoAvverso(String idCentro, String CodiceFiscale,String evento, int severita, String data, String noteOpz) {
        this.idCentro = idCentro;
        this.CodiceFiscale = CodiceFiscale;
        this.evento = evento;
        this.severita = severita;
        this.data = data;
        this.noteOpz = noteOpz;

    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
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
        if(data == null){
            return "EventoAvverso{" +
                    "idCentro='" + idCentro + '\'' +
                    ", CodiceFiscale='" + CodiceFiscale + '\'' +
                    ", evento='" + evento + '\'' +
                    ", severita=" + severita +
                    ", noteOpz='" + noteOpz + '\'' +
                    '}';
        }else{
            return "EventoAvverso{" +
                    "idCentro='" + idCentro + '\'' +
                    ", CodiceFiscale='" + CodiceFiscale + '\'' +
                    ", evento='" + evento + '\'' +
                    ", severita=" + severita +
                    ", data='" + data + '\'' +
                    ", noteOpz='" + noteOpz + '\'' +
                    '}';

        }

    }
}
