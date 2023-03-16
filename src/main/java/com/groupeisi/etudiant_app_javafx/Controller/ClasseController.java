package com.groupeisi.etudiant_app_javafx.Controller;

import com.groupeisi.etudiant_app_javafx.Services.ClasseImpl;
import com.groupeisi.etudiant_app_javafx.Services.IClasse;
import com.groupeisi.etudiant_app_javafx.entity.Classe;
import com.groupeisi.etudiant_app_javafx.tools.Notification;
import com.groupeisi.etudiant_app_javafx.tools.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClasseController implements Initializable {
    private final IClasse iClasse = new ClasseImpl();
    private int id;
    @FXML
    private Button deconnecterBtn;

    @FXML
    private Button enregistreBtn;

    @FXML
    private TableView<Classe> tableClasse;
    @FXML
    private TableColumn<Classe, Integer> idC;
    @FXML
    private TableColumn<Classe, Integer> effectifC;

    @FXML
    private TableColumn<Classe, String> nomC;

    @FXML
    private TextField nomTfd;
    @FXML
    private Button modifierBtn;

    @FXML
    private Button supprimerBtn;


    @FXML
    private Button viderBtn;

    @FXML
    void charge(MouseEvent event) {
        Classe c = tableClasse.getSelectionModel().getSelectedItem();
        nomTfd.setText(c.getNom());
        id = c.getId();
        enregistreBtn.setDisable(true);
        modifierBtn.setDisable(false);
        supprimerBtn.setDisable(false);

    }

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void deconnect(ActionEvent event) throws IOException {
        Notification.NotifSuccess("Info", "Crud etudiants!");
        Outils.load(event, "CRUD", "/Fxml/etudiant.fxml");
    }

    @FXML
    void delete(ActionEvent event) {
        if (iClasse.get(id).getEffectif() == 0) {
            if (iClasse.delete(id) == 1) {
                Notification.NotifSuccess("Succes", "Suppression reussi!");
                loadTable();
                vider();
            } else {
                Notification.NotifError("Error", "Suppression echouée!!");
            }
        } else {
            Notification.NotifError("Info", "Impossible de supprimer cette classe!");
        }

    }

    @FXML
    void save(ActionEvent event) {
        String nom = nomTfd.getText();
        if (nom.equals("")){
            Notification.NotifError("Error","Veuillez remplir le champ");
        }else {
            Classe classe = new Classe(nom);
            if (iClasse.add(classe)==1) {
                Notification.NotifSuccess("Success", "Enregistrement fait!");
                loadTable();
                vider();
            }
            else
                Notification.NotifError("Erreur","Echec d'enregistrment!");
        }

    }

    @FXML
    void update(ActionEvent event) {

        String nom = nomTfd.getText();
        if (nom.equals("")){
            Notification.NotifError("Error","Veuillez remplir le champ");
        }else {
            Classe classe = iClasse.get(id);
            classe.setNom(nom);
            if (iClasse.update(classe)==1) {
                Notification.NotifSuccess("Success", "Modification fait!");
                loadTable();
                vider();
            }
            else
                Notification.NotifError("Erreur","Echec de Modification!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);
        loadTable();
    }

    private void loadTable() {
        ObservableList<Classe> classes = FXCollections.observableList(iClasse.list());
        tableClasse.setItems(classes);
        idC.setCellValueFactory(new PropertyValueFactory<Classe, Integer>("id"));
        nomC.setCellValueFactory(new PropertyValueFactory<Classe, String>("nom"));
        effectifC.setCellValueFactory(new PropertyValueFactory<Classe, Integer>("effectif"));

    }

    private void vider() {
        nomTfd.setText("");
        id = 0;
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);
        enregistreBtn.setDisable(false);
    }
}
