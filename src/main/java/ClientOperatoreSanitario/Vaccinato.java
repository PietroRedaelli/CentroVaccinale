package ClientOperatoreSanitario;

import java.io.Serializable;

public class Vaccinato implements Serializable {

    String nome;
    String cognome;
    String nomeCentro;
    String idVacc;
    String codiceFisc;
    String data;
    String vaccino;

    public Vaccinato(String nome, String cognome, String nomeCentro, String idVacc, String codiceFisc, String data, String vaccino) {
        this.nome = nome;
        this.cognome = cognome;
        this.nomeCentro = nomeCentro;
        this.idVacc = idVacc;
        this.codiceFisc = codiceFisc;
        this.data = data;
        this.vaccino = vaccino;
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

}
