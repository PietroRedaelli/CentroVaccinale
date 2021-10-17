package Grafics;

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

//Classe che genera una finestra che riassume i dati inseriti prima di salvarli nel database
public class ConfirmBoxCentro {

    static boolean risposta;

    public static boolean start(String nomeCentro, String indirizzo, String tipologia) {
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
        centro.setText("Nome:  " + nomeCentro);
        centro.setFont(Font.font(18));

        Label ind = new Label();
        ind.setText("Indirizzo:  " + indirizzo);
        ind.setFont(Font.font(18));

        Label tipo = new Label();
        tipo.setText("Tipologia:   " + tipologia);
        tipo.setFont(Font.font(18));

        Button bAnnulla = new Button("Annulla");
        bAnnulla.setFont(Font.font(18));
        Button bConferma = new Button("Conferma");
        bConferma.setFont(Font.font(18));

        //comportamento dei due bottoni presenti
        bAnnulla.setOnAction(e -> {
            risposta = false;
            stage.close();
        });

        bConferma.setOnAction(e -> {
            if (controlloDB()) {
            risposta = true;
            //OperatoreSanitarioAPP operatoreSanitarioAPP = new OperatoreSanitarioAPP();
            //operatoreSanitarioAPP.registraCV(centro);
            stage.close();
            }
            else {
                conferma.setText("Centro già registrato!");
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
        vBox.getChildren().addAll(vBox2, centro, ind, tipo, hBox);
        Scene scena = new Scene(vBox);
        stage.setScene(scena);
        stage.showAndWait();

        return risposta;
    }

    //funzione che controlla se il centro che si sta inserendo non sia già stato registrato nel database
    private static boolean controlloDB() {
        return true;
    }
}
