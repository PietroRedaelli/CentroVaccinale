package ClientOperatoreSanitario;

import java.io.Serializable;

public class Vaccinato implements Serializable {

    private static final long seriaVersionUID = 1L;

    String nome;
    String cognome;
    int centroVacc;
    long idVacc;
    String codiceFisc;
    String data;
    String vaccino;
    int dose;

    public Vaccinato(String nome, String cognome, int centroVacc, long idVacc, String codiceFisc, String data, String vaccino, int dose) {
        this.nome = nome;
        this.cognome = cognome;
        this.centroVacc = centroVacc;
        this.idVacc = idVacc;
        this.codiceFisc = codiceFisc;
        this.data = data;
        this.vaccino = vaccino;
        this.dose = dose;
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

    public int getCentroVacc() {
        return centroVacc;
    }

    public void setCentroVacc(int centroVacc) {
        this.centroVacc = centroVacc;
    }

    public long getIdVacc() {
        return idVacc;
    }

    public void setIdVacc(long idVacc) {
        this.idVacc = idVacc;
    }

    public String getCodiceFisc() {
        return codiceFisc;
    }

    public void setCodiceFisc(String codiceFisc) {
        this.codiceFisc = codiceFisc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVaccino() {
        return vaccino;
    }

    public void setVaccino(String vaccino) {
        this.vaccino = vaccino;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + ", " + codiceFisc + ", " + centroVacc + " " + vaccino + " " + dose + " " + idVacc + ", " + data;
    }
}
