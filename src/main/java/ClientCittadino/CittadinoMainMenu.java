package ClientCittadino;

import javafx.event.ActionEvent;
import java.io.IOException;

public class CittadinoMainMenu {

    public void apriScenaLogin(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoRegEvento.fxml");
    }

    public void apriScenaRegistrazione(ActionEvent actionEvent) throws IOException{
        AppCittadino.setRoot("cittadinoRegistrazione.fxml");
    }
    public void informazioniShow(ActionEvent actionEvent) throws IOException {
        AppCittadino.setRoot("cittadinoSceltaCentro.fxml");
    }
}