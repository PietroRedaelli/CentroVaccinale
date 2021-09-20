package ClientOperatoreSanitario;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        OperatoreSanitarioAPP operatoreSanitarioAPP = new OperatoreSanitarioAPP();
        operatoreSanitarioAPP.registraCV();
        //operatoreSanitarioAPP.registraVaccinato();
    }
}
