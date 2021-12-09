package ServerPackage;

import java.io.Serializable;

public class CentroVaccinale implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nomeCentro;
    private String comune;
    //private String qualif;
    private String indirizzoCentro;
    private String civico;
    private String sigla;
    private int cap;
    private String tipo;

    public CentroVaccinale(){
    }

    public CentroVaccinale(String nomeCentro, String comune, String indirizzoCentro, String civico, String sigla, int cap, String tipo) {
        this.nomeCentro = nomeCentro;
        this.comune = comune;
        this.indirizzoCentro = indirizzoCentro;
        this.civico = civico;
        this.sigla = sigla;
        this.cap = cap;
        this.tipo = tipo;
    }

    public CentroVaccinale(int ID, String nomeCentro, String comune, String indirizzoCentro, String civico, String sigla, int cap, String tipo) {
        this(nomeCentro, comune, indirizzoCentro, civico, sigla, cap, tipo);
        this.ID = ID;
    }

    public CentroVaccinale(int id, String centro) {
        this.ID = id;
        this.nomeCentro = centro;
    }

    @Override
    public String toString() {
        return nomeCentro + " " + tipo + ", " + indirizzoCentro + " " + civico + ", " + comune + " " + sigla + " " + cap;
    }

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getIndirizzoCentro() {
        return indirizzoCentro;
    }

    public void setIndirizzoCentro(String indirizzoCentro) {
        this.indirizzoCentro = indirizzoCentro;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getID() {return ID;}

    public void setID(int ID) { this.ID = ID; }
}
