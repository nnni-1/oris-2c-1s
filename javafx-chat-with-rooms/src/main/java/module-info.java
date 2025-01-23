module ru.alexeev.javafxchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires jdk.jfr;

//    opens com.fasterxml.jackson.databind to javafx.fxml;
//    exports ru.alexeev.javafxchat.model;

    opens ru.alexeev.javafxchat to com.fasterxml.jackson.databind, javafx.fxml;
    exports ru.alexeev.javafxchat;

    exports ru.alexeev.javafxchat.model;

    opens ru.alexeev.javafxchat.model to com.fasterxml.jackson.databind;

}