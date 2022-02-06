module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.controlsfx.controls;

    exports ServerCV;
    opens ServerCV to javafx.fxml;
    exports ClientCVOperatoreSanitario;
    opens ClientCVOperatoreSanitario to javafx.fxml;
    exports ClientCVCittadino;
    opens ClientCVCittadino to javafx.fxml;
}