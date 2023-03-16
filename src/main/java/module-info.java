module com.groupeisi.etudiant_app_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires TrayNotification;

    opens com.groupeisi.etudiant_app_javafx to javafx.fxml;
    opens com.groupeisi.etudiant_app_javafx.Controller to javafx.fxml;
    exports com.groupeisi.etudiant_app_javafx;
    exports com.groupeisi.etudiant_app_javafx.Controller;
    exports com.groupeisi.etudiant_app_javafx.Services;
    exports com.groupeisi.etudiant_app_javafx.entity;
}