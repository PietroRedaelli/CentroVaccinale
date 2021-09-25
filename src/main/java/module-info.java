module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.controlsfx.controls;

    exports ServerPackage;
    exports ClientOperatoreSanitario;
    opens ClientOperatoreSanitario to javafx.fxml;
}