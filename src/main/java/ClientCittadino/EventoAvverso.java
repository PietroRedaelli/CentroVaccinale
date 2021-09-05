package ClientCittadino;

import java.io.Serializable;

public class EventoAvverso implements Serializable {

    String evento;
    int severita;
    String data;
    String noteOpz;


    public EventoAvverso(String evento, int severita, String data, String noteOpz) {
        this.evento = evento;
        this.severita = severita;
        this.data = data;
        this.noteOpz = noteOpz;
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

}
