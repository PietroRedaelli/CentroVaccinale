package ClientOperatoreSanitario;

import java.io.IOException;

import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import javafx.event.ActionEvent;

public class OperatoreSceltaReg {

    public void apriScenaRegCentro(ActionEvent actionEvent) throws IOException {
        OperatoreSanitarioAPP.setRoot("operatoreRegCentro");
        //Scene scene = new Scene(new FXMLLoader(App.class.getResource("operatoreRegCentro.fxml")).load()); carica la scena desiderata
    }

    public void apriScenaRegVacc(ActionEvent actionEvent) throws IOException{
        OperatoreSanitarioAPP.setRoot("clienteRegEvento");
    }

}
