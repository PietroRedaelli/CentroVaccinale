module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    opens org.example to javafx.fxml;
    exports org.example;
    exports ServerPackage;
}