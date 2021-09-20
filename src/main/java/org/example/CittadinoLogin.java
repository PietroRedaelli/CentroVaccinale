package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CittadinoLogin implements Initializable {
    @FXML private TextField username;
    @FXML private TextField password;
    public void loginUtente(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void annulla() throws IOException {
        AppOperatore.setRoot("cittadinoMainMenu");
    }
    public void login(){

    }
}