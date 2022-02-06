package clientCVCittadino;

import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * La classe permette il caricamento dei file fxml relativi al menù
 * iniziale dell'interfaccia grafica di un ClientCittadino.
 * @author Pietro
 * @version 1.0
 */
public class CittadinoMainMenu {

    /**
     * Il metodo permette di aprire l'interfaccia grafica relativa al login di un cittadino.
     * @param actionEvent
     * @throws IOException
     */
    public void apriScenaLogin(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoRegEvento.fxml");
    }

    /**
     * Il metodo permette di aprire l'interfaccia grafica relativa alla registrazione di un cittadino.
     * @param actionEvent
     * @throws IOException
     */
    public void apriScenaRegistrazione(ActionEvent actionEvent) throws IOException{
        AppCittadino.setRoot("cittadinoRegistrazione.fxml");
    }

    /**
     * Il metodo permette di aprire l'interfaccia grafica relativa alle informazioni sui centri
     * vaccinali che un cittadino può visualizzare senza aver effettuato il login.
     * @param actionEvent
     * @throws IOException
     */
    public void informazioniShow(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoSceltaCentro.fxml");
    }
}