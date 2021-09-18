package org.example;

import java.io.IOException;
import javafx.event.ActionEvent;

public class OperatoreSceltaReg {

    public void apriScenaRegCentro(ActionEvent actionEvent) throws IOException {
        App.setRoot("operatoreRegCentro");
        //Scene scene = new Scene(new FXMLLoader(App.class.getResource("operatoreRegCentro.fxml")).load()); carica la scena desiderata
    }

    public void apriScenaRegVacc(ActionEvent actionEvent) throws IOException{
        App.setRoot("operatoreRegVacc");
    }

}
