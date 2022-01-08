package Grafics;

import ClientOperatoreSanitario.OperatoreSanitarioAPP;
import ClientOperatoreSanitario.CentroVaccinale;
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

//Classe che genera una finestra dove vengono riassunti i dati inseriti prima di salvarli definitivamente nel database
public class ConfirmBoxCentro {

    static boolean risposta = false;
    private static CentroVaccinale cv;
    public static String error = "";

    //funzione chiamata per generare la nuova finestra di conferma
    public static boolean start(CentroVaccinale centroVaccinale) {

        cv = centroVaccinale;

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
        centro.setText("Nome:  " + centroVaccinale.getNomeCentro());
        centro.setFont(Font.font(18));

        Label ind = new Label();
        ind.setText("Indirizzo:  " + centroVaccinale.getIndirizzoCentro() +
                " " + centroVaccinale.getCivico());
        ind.setFont(Font.font(18));


        Label citta = new Label();
        citta.setText("Città:  " + centroVaccinale.getComune() +
                " ("+ centroVaccinale.getSigla()+") " +
                " " + centroVaccinale.getCap());
        citta.setFont(Font.font(18));


        Label tipo = new Label();
        tipo.setText("Tipologia:   " + centroVaccinale.getTipo());
        tipo.setFont(Font.font(18));

        Button bAnnulla = new Button("Annulla");
        bAnnulla.setFont(Font.font(18));
        Button bConferma = new Button("Conferma");
        bConferma.setFont(Font.font(18));

        /*premendo il bottone 'annulla' la pagina corrente si chiude e si ritorna alla pagina di registrazione del centro
        per modificare eventuali dati errati*/
        bAnnulla.setOnAction(e -> {
            risposta = false;
            stage.close();
        });

        /*premendo il bottone 'conferma' la pagina corrente si chiude, il centro viene salvato nel database e si ritorna
        alla pagina di registrazione in cui tutte le informazioni inserite vengono cancellate per poterne registrare
        comodamente un altro*/
        bConferma.setOnAction(e -> {
            //si cerca il contrario perché se la funzione ritorna 'false' allora non esiste un centro uguale a quello inserito,
            // quindi posso registrare quello che sto inserendo
            if (controlloEsistenzaCentro()) {
                risposta = true;
                OperatoreSanitarioAPP.registraCentroVaccinale(cv);
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
        vBox.getChildren().addAll(vBox2, centro, ind, citta, tipo, hBox);
        Scene scena = new Scene(vBox);
        stage.setScene(scena);
        stage.showAndWait();

        return risposta;
    }

    //funzione che controlla se il centro che si sta inserendo non sia già stato registrato nel database
    private static boolean controlloEsistenzaCentro() {
        return OperatoreSanitarioAPP.controllaCentro(cv);
    }
}
