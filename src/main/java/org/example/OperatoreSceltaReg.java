package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class OperatoreSceltaReg {

    public void apriScenaRegCentro(ActionEvent actionEvent) throws IOException {
        App.setRoot("operatoreRegCentro");
    }

    public void apriScenaRegVacc(ActionEvent actionEvent) throws IOException{
        App.setRoot("operatoreRegVacc");
    }

}
