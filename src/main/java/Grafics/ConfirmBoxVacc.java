package Grafics;

import ClientCVOperatoreSanitario.AppOperatoreSanitario;
import ClientCVOperatoreSanitario.Vaccinato;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * La classe ConfirmBoxVacc ha lo scopo di generare la finestra dove vengono riassunti
 * i dati inseriti relativi a un vaccinato prima di salvarli definitivamente nel database.
 * @author Pietro
 * @version 1.0
 */
public class ConfirmBoxVacc {

    private static boolean risposta;
    private static Vaccinato vacc;
    private static final AppOperatoreSanitario OS = new AppOperatoreSanitario();
    private static String error = "";

    /**
     * Il metodo ha la funzione di generare la finestra di conferma.
     */
    public static boolean start(Vaccinato vaccinato, String centroVaccinale) {

        vacc = vaccinato;

        //creazione della pagina
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Conferma");
        stage.setMinWidth(400);
        stage.setMinHeight(200);

        Label conferma = new Label();
        conferma.setText("Confermi i dati inseriti?");
        conferma.setFont(Font.font(18));

        Label centro = new Label();
        centro.setText("Dati Centro:  "+ centroVaccinale);
        centro.setFont(Font.font(18));

        Label persona = new Label();
        persona.setText("Vaccinato:  " + vaccinato.getNome() + " " + vaccinato.getCognome());
        persona.setFont(Font.font(18));

        Label codice = new Label();
        codice.setText("Codice Fiscale:   " + vaccinato.getCodiceFisc());
        codice.setFont(Font.font(18));

        Label giorno = new Label();
        giorno.setText("Data:   " + vaccinato.getData());
        giorno.setFont(Font.font(18));

        Label dose = new Label();
        dose.setText("Vaccino:   " + vaccinato.getVaccino() + ", dose " + vaccinato.getDose());
        dose.setFont(Font.font(18));

        Label codiceID = new Label();
        codiceID.setText("Codice ID:   " + vaccinato.getIdVacc());
        codiceID.setFont(Font.font(18));

        Button bAnnulla = new Button("Annulla");
        bAnnulla.setFont(Font.font(18));
        Button bConferma = new Button("Conferma");
        bConferma.setFont(Font.font(18));

        /*premendo il bottone 'annulla' la pagina corrente si chiude e si ritorna alla pagina di registrazione del vaccinato
        per modificare eventuali dati errati*/
        bAnnulla.setOnAction(e -> {
            risposta = false;
            stage.close();
        });

        /*premendo il bottone 'conferma' la pagina corrente si chiude, il vaccinato viene salvato nel database e si ritorna
        alla pagina di registrazione in cui tutte le informazioni inserite vengono cancellate per poterne registrare
        comodamente un altro*/
        bConferma.setOnAction(e -> {
            if (controlloDB()) {
                risposta = true;
                AppOperatoreSanitario appOperatoreSanitario = new AppOperatoreSanitario();
                appOperatoreSanitario.registraVaccinato(vaccinato);
                stage.close();
            } else {
                conferma.setText(error);
                conferma.setStyle("-fx-text-fill: red;");
            }
        });

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox2 = new VBox(15);
        vBox2.setPadding(new Insets(10));
        vBox2.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(100);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(bAnnulla, bConferma);
        vBox2.getChildren().add(conferma);
        vBox.getChildren().addAll(vBox2, centro, persona, codice, giorno, dose, codiceID, hBox);
        Scene scena = new Scene(vBox);
        stage.setScene(scena);
        stage.showAndWait();

        return risposta;
    }

    /**
     * Il metodo ha la funzione di controllare se il vaccinato inserito
     * non sia già stato registrato nel database
     */
    private static boolean controlloDB() {
        return OS.controllaVaccinato(vacc);
    }

    public static void setError(String errore) {
        error = errore;
    }
}