module com.example.diamond_shop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.diamond_shop to javafx.fxml;
    exports com.example.diamond_shop;
    requires java.sql;
    requires java.desktop;
}