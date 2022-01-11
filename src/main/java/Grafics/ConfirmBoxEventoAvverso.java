package Grafics;

import ClientCittadino.AppCittadino;
import ClientCittadino.CittadinoRegEvento;
import ClientCittadino.EventoAvverso;
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

import java.rmi.RemoteException;

public class ConfirmBoxEventoAvverso {

    private static EventoAvverso eventoAvverso;
    public static String error = "";
    private static boolean risposta = false;

    //funzione chiamata per generare la nuova finestra di conferma
    public static boolean start(EventoAvverso evento) {

        eventoAvverso = evento;

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
        centro.setText("Nome Centro:   "+ eventoAvverso.getNomeCentro());
        centro.setFont(Font.font(18));

        Label persona = new Label();
        persona.setText("ID Vaccinato:   " + eventoAvverso.getIdVacc());
        persona.setFont(Font.font(18));

        Label codice = new Label();
        codice.setText("Codice Fiscale:   " + eventoAvverso.getCodiceFiscale());
        codice.setFont(Font.font(18));

        Label giorno = new Label();
        giorno.setText("Data:   " + eventoAvverso.getData());
        giorno.setFont(Font.font(18));

        Label dose = new Label();
        dose.setText("Tipo evento riscontrato:   " + eventoAvverso.getEvento());
        dose.setFont(Font.font(18));


        Label codiceID = new Label();
        codiceID.setText("Note:\n " + eventoAvverso.getNoteOpz());
        codiceID.setFont(Font.font(18));

        Button bAnnulla = new Button("Annulla");
        bAnnulla.setFont(Font.font(18));
        Button bConferma = new Button("Conferma");
        bConferma.setFont(Font.font(18));

        /*premendo il bottone 'annulla' la pagina corrente si chiude e si ritorna alla pagina di registrazione del vaccinato
        per modificare eventuali dati errati*/
        bAnnulla.setOnAction(e -> {
            stage.close();
        });

        /*premendo il bottone 'conferma' la pagina corrente si chiude, il vaccinato viene salvato nel database e si ritorna
        alla pagina di registrazione in cui tutte le informazioni inserite vengono cancellate per poterne registrare
        comodamente un altro*/
        bConferma.setOnAction(e -> {
            if (controlloEventoAvversoDB()) {
                System.out.println(CittadinoRegEvento.cittadino.getCodiceFiscale()+ " inserisce: "+ eventoAvverso);
                risposta = true;
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

    //funzione che controlla se il vaccinato che si sta inserendo non sia già stato registrato nel database
    private static boolean controlloEventoAvversoDB() {
        try {
            error = AppCittadino.si.inserisciEventoAvverso(eventoAvverso);
            if(error.equals("Evento già registrato !") || error.equals("Errore di connessione con il DB !")){
                return false;
            }
        } catch (RemoteException e) {
            error = e.getMessage();
            return false;
        }
        return true;
    }
}
