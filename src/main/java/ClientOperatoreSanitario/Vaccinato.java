package ClientOperatoreSanitario;

import java.io.Serializable;

public class Vaccinato implements Serializable {

    private static final long serialVersionUID = 1L;

    String nome;
    String cognome;
    String nomeCentro;
    String idVacc;
    String codiceFisc;
    String data;
    String vaccino;
    String dose;

    public Vaccinato(String nome, String cognome, String nomeCentro, String idVacc, String codiceFisc, String data, String vaccino, String dose) {
        this.nome = nome;
        this.cognome = cognome;
        this.nomeCentro = nomeCentro;
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

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public String getIdVacc() {
        return idVacc;
    }

    public void setIdVacc(String idVacc) {
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

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Vaccinato{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nomeCentro='" + nomeCentro + '\'' +
                ", idVacc='" + idVacc + '\'' +
                ", codiceFisc='" + codiceFisc + '\'' +
                ", data='" + data + '\'' +
                ", vaccino='" + vaccino + '\'' +
                ", dose='" + dose + '\'' +
                '}';
    }
}
