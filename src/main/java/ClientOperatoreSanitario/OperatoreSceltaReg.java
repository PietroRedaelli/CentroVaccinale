package ClientOperatoreSanitario;

import java.io.IOException;
import javafx.event.ActionEvent;

public class OperatoreSceltaReg {

    public void apriScenaRegCentro(ActionEvent actionEvent) throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreRegCentro.fxml");
    }

    public void apriScenaRegVacc(ActionEvent actionEvent) throws IOException{
        OperatoreSanitarioAPP.setRoot("operatoreRegVacc.fxml");
    }

}