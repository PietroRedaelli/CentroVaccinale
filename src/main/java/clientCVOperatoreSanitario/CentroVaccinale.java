package clientCVOperatoreSanitario;

import java.io.Serializable;

/**
 * La classe CentroVaccinale descrive, definisce e permette di costruire un centro vaccinale.
 * @author Pietro
 * @version 1.0
 */
public class CentroVaccinale implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nomeCentro;
    private String comune;
    private String indirizzoCentro;
    private String civico;
    private String sigla;
    private int cap;
    private String tipo;

    public CentroVaccinale(){}

    /**
     * Il costruttore crea una nuovo centro vaccinale.
     * @param nomeCentro indica il nome del centro vaccinale.
     * @param comune indica il comune del centro vaccinale.
     * @param indirizzoCentro indica l'indirizzo del centro vaccinale.
     * @param civico indica il numero civico del centro vaccinale.
     * @param sigla indica la sigla della provincia di ubicazione del centro vaccinale.
     * @param cap indica il cap del centro vaccinale.
     * @param tipo indica il tipo di centro vaccinale (hub, struttura, ospedaliero..).
     */
    public CentroVaccinale(String nomeCentro, String comune, String indirizzoCentro, String civico, String sigla, int cap, String tipo) {
        this.nomeCentro = nomeCentro;
        this.comune = comune;
        this.indirizzoCentro = indirizzoCentro;
        this.civico = civico;
        this.sigla = sigla;
        this.cap = cap;
        this.tipo = tipo;
    }

    /**
     * Il costruttore crea un nuovo centro vaccinale e gli assegna un ID univoco
     * @param ID indica l'ID del centro vaccinale.
     * @param nomeCentro indica il nome del centro vaccinale.
     * @param comune indica il comune del centro vaccinale.
     * @param indirizzoCentro indica l'indirizzo del centro vaccinale.
     * @param civico indica il numero civico del centro vaccinale.
     * @param sigla indica la sigla della provincia di ubicazione del centro vaccinale.
     * @param cap indica il cap del centro vaccinale.
     * @param tipo indica il tipo di centro vaccinale (hub, struttura, ospedaliero..).
     */
    public CentroVaccinale(int ID, String nomeCentro, String comune, String indirizzoCentro, String civico, String sigla, int cap, String tipo) {
        this(nomeCentro, comune, indirizzoCentro, civico, sigla, cap, tipo);
        this.ID = ID;
    }

    /**
     * Il costruttore crea un nuovo centro vaccinale secondo un ID e un nome.
     * @param id indica l'ID del centro vaccinale.
     * @param centro indica il nome del centro vaccinale.
     */
    public CentroVaccinale(int id, String centro) {
        this.ID = id;
        this.nomeCentro = centro;
    }

    @Override
    public String toString() {
        if(this.indirizzoCentro == null){
            return  "ID: " + ID + ", " +
                    "Nome: " + nomeCentro ;
        }else{
            return  "Nome: " + nomeCentro + ", " +
                    "Indirizzo: " + indirizzoCentro +" "+ civico + ", " +
                    "Comune: " +comune +" ("+ sigla + "), " +
                    "Cap: " + cap + ", " +
                    "Tipo: " + tipo ;
        }
    }

    /**
     * Il metodo restituisce il nome del centro vaccinale.
     * @return il nome del centro vaccinale.
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * Il metodo permette di definire un nuovo nome di un centro vaccinale.
     * @param nomeCentro indica un nuovo nome del centro vaccinale.
     */
    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    /**
     * Il metodo restituisce il comune di un centro vaccinale.
     * @return il comune di un centro vaccinale.
     */
    public String getComune() {
        return comune;
    }

    /**
     * Il metodo permette di definire un nuovo comune di un centro vaccinale.
     * @param comune indica il nuovo comune di un centro vaccinale.
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * Il metodo restituisce l'indirizzo del centro vaccinale.
     * @return l'indirizzo del centro vaccinale.
     */
    public String getIndirizzoCentro() {
        return indirizzoCentro;
    }

    /**
     * Il metodo permette di definire un nuovo indirizzo per un centro vaccinale.
     * @param indirizzoCentro indica un nuovo indirizzo di un centro vaccinale.
     */
    public void setIndirizzoCentro(String indirizzoCentro) {
        this.indirizzoCentro = indirizzoCentro;
    }

    /**
     * Il metodo restituisce il numero civico del centro vaccinale.
     * @return il numero civico del centro vaccinale.
     */
    public String getCivico() { return civico; }

    /**
     * Il metodo permette di definire un nuovo numero civico per un centro vaccinale.
     * @param civico indica un nuovo numero civico di un centro vaccinale.
     */
    public void setCivico(String civico) {
        this.civico = civico;
    }

    /**
     * Il metodo restituisce la sigla della provincia di appartenenza di un centro vaccinale.
     * @return  la sigla della provincia di appartenenza del centro vaccinale.
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Il metodo permette di definire una nuova sigla della provincia di appartenenza di un centro vaccinale.
     * @param sigla indica la nuova sigla della provincia di appartenenza di un centro vaccinale.
     */
    public void setSigla(String sigla) { this.sigla = sigla; }

    /**
     * Il metodo restituisce il cap del comnune di appartenenza del centro vaccinale.
     * @return il cap del comnune di appartenenza del centro vaccinale.
     */
    public int getCap() { return cap; }

    /**
     * Il metodo permette di definire un nuovo CAP del comune di appartenenza di un centro vaccinale.
     * @param cap indica il nuovo CAP del comune di appartenenza di un centro vaccinale.
     */
    public void setCap(int cap) {
        this.cap = cap;
    }

    /**
     * Il metodo restituisce il tipo di centro vaccinale (hub, ospedaliero, ...).
     * @return il tipo di centro vaccinale (hub, ospedaliero, ...).
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Il metodo permette di definire un nuovo tipo relativo a un centro vaccinale.
     * @param tipo indica il nuovo tipo di un centro vaccinale.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Il metodo restituisce l'ID univoco del centro vaccinale.
     * @return l'ID univoco del centro vaccinale.
     */
    public int getID() {return ID;}

    /**
     * Il metodo permette di definire un nuovo ID di un centro vaccinale.
     * @param ID indica il nuovo ID di un centro vaccinale.
     */
    public void setID(int ID) { this.ID = ID; }
}