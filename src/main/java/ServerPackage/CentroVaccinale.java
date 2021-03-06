package ServerPackage;

import java.io.Serializable;

public class CentroVaccinale implements Serializable {

    String nomeCentro;
    String comune;
    String qualif;
    String nomeInd;
    String civico;
    String sigla;
    String cap;
    String tipo;

    public CentroVaccinale(){
    }

    public CentroVaccinale(String nomeCentro, String comune, String qualif, String nomeInd, String civico, String sigla, String cap, String tipo) {
        this.nomeCentro = nomeCentro;
        this.comune = comune;
        this.qualif = qualif;
        this.nomeInd = nomeInd;
        this.civico = civico;
        this.sigla = sigla;
        this.cap = cap;
        this.tipo = tipo;
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

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
