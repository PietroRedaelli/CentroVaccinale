package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CittadinoMainMenu {
    @FXML
    Button log;
    @FXML
    Button registrazione;

    public void apriScenaLogin(ActionEvent actionEvent) throws IOException {
        AppOperatore.setRoot("cittadinoLogin");
        //Scene scene = new Scene(new FXMLLoader(App.class.getResource("operatoreRegCentro.fxml")).load()); carica la scena desiderata
    }

    public void apriScenaRegistrazione(ActionEvent actionEvent) throws IOException{
        AppOperatore.setRoot("cittadinoRegistrazione");
    }

}
