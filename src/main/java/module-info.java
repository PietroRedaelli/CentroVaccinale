module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.controlsfx.controls;

    exports serverCV;
    opens serverCV to javafx.fxml;
    exports clientCVOperatoreSanitario;
    opens clientCVOperatoreSanitario to javafx.fxml;
    exports clientCVCittadino;
    opens clientCVCittadino to javafx.fxml;
}