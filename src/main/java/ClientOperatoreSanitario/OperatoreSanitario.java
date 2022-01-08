package ClientOperatoreSanitario;

import java.io.Serializable;

public class OperatoreSanitario implements Serializable {

    private static final long serialVersionUID = 1L;

    //Dati
    private int id;//numero dell'operatore sanitario datogli dal Server

    //Costruttori
    public OperatoreSanitario(int num) {
        id = num;
    }
    public OperatoreSanitario() {

    }

    //Metodi
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Operatore Sanitario (" +id +")";
    }
}
