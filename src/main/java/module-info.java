module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;

    exports ServerPackage;
    exports ClientOperatoreSanitario;
    opens ClientOperatoreSanitario to javafx.fxml;
}