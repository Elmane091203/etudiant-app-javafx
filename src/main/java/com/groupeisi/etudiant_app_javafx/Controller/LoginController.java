package com.groupeisi.etudiant_app_javafx.Controller;

import com.groupeisi.etudiant_app_javafx.Services.IUser;
import com.groupeisi.etudiant_app_javafx.Services.UserImpl;
import com.groupeisi.etudiant_app_javafx.entity.User;
import com.groupeisi.etudiant_app_javafx.tools.Notification;
import com.groupeisi.etudiant_app_javafx.tools.Outils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class LoginController {

    @FXML
    private Button annulerBtn;

    @FXML
    private Button connexionBtn;

    @FXML
    private TextField emailTfd;

    @FXML
    private PasswordField passwordTfd;
    private IUser userDao = new UserImpl();

    @FXML
    void annuler(ActionEvent event) {
        exit();
    }

    @FXML
    void getLogin(ActionEvent event) throws IOException {
            String email = emailTfd.getText();
            String password = passwordTfd.getText();
            if (email.equals("") || password.equals("")) {
                Notification.NotifError("Warning", "Champs Obligatoires");
            } else {
                User user = userDao.seConnecter(email, password);
                if (user == null)
                    Notification.NotifError("Erreur", "Email et/ou password Incorrects");
                else {
                    try {
                        Notification.NotifSuccess("Success", "Connexion Reussie");
                        Outils.load(event, "CRUD", "/Fxml/etudiant.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

    }
}
