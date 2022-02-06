package ClientOperatoreSanitario;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * La classe OperatoreSceltaReg ha lo scopo di caricare i file fxml necessari alle funzionalità di
 * scelta di registrazione di un centro vaccinale e di registrazione di un vaccinato da parte
 * di un operatore sanitario.
 * @author Pietro
 * @version 1.0
 */
public class OperatoreSceltaReg {

    public void apriScenaRegCentro(ActionEvent actionEvent) throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreRegCentro.fxml");
    }

    public void apriScenaRegVacc(ActionEvent actionEvent) throws IOException{
        OperatoreSanitarioAPP.setRoot("operatoreRegVacc.fxml");
    }
}