module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.controlsfx.controls;

    exports ServerPackage;
    opens ServerPackage to javafx.fxml;
    exports ClientOperatoreSanitario;
    opens ClientOperatoreSanitario to javafx.fxml;
    exports ClientCittadino;
    opens ClientCittadino to javafx.fxml;
}