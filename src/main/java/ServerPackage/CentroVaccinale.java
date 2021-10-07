package ServerPackage;

import java.io.Serializable;

public class CentroVaccinale implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nomeCentro;
    private String comune;
    private String qualif;
    private String nomeInd;
    private String civico;
    private String sigla;
    private int cap;
    private String tipo;

    public CentroVaccinale(){
    }

    public CentroVaccinale(String nomeCentro, String comune, String qualif, String nomeInd, String civico, String sigla, int cap, String tipo) {
        this.nomeCentro = nomeCentro;
        this.comune = comune;
        this.qualif = qualif;
        this.nomeInd = nomeInd;
        this.civico = civico;
        this.sigla = sigla;
        this.cap = cap;
        this.tipo = tipo;
    }

    public CentroVaccinale(int ID, String nomeCentro, String comune, String qualif, String nomeInd, String civico, String sigla, int cap, String tipo) {
        this(nomeCentro, comune, qualif, nomeInd, civico, sigla, cap, tipo);
        this.ID = ID;
    }

    public CentroVaccinale(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    @Override
    public String toString() {
        return nomeCentro + " " + tipo + ", " + qualif + " " + nomeInd + " " + civico + ", " + comune + " " + sigla + " " + cap;
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

    public String getQualif() {
        return qualif;
    }

    public void setQualif(String qualif) {
        this.qualif = qualif;
    }

    public String getNomeInd() {
        return nomeInd;
    }

    public void setNomeInd(String nomeInd) {
        this.nomeInd = nomeInd;
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
