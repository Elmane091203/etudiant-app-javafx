package com.groupeisi.etudiant_app_javafx.Controller;


import com.groupeisi.etudiant_app_javafx.Services.ClasseImpl;
import com.groupeisi.etudiant_app_javafx.Services.EtudiantImpl;
import com.groupeisi.etudiant_app_javafx.Services.IEtudiant;
import com.groupeisi.etudiant_app_javafx.entity.Classe;
import com.groupeisi.etudiant_app_javafx.entity.Etudiant;
import com.groupeisi.etudiant_app_javafx.tools.Notification;
import com.groupeisi.etudiant_app_javafx.tools.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {
    private IEtudiant iEtudiant = new EtudiantImpl();
    private int id=0;
    /**
     * iEtudiant est une variable qui permet d'avoir une connexion à la table etudiant
     * declaration des variables du tableau
     */
    @FXML
    private TableView<Etudiant> tableEtudiant;
    @FXML
    private TableColumn<Etudiant, String> MatriculeC;
    @FXML
    private TableColumn<Etudiant, String> classeC;
    @FXML
    private TableColumn<Etudiant, Integer> idC;
    @FXML
    private TableColumn<Etudiant, Double> moyenneC;
    @FXML
    private TableColumn<Etudiant, String> prenomC;
    @FXML
    private TableColumn<Etudiant, String> nomC;

    /**
     * Declaration des differents controls de de fenetres
     */
    @FXML
    private Button classeBtn;


    @FXML
    private ComboBox<String> classeCbx;

    @FXML
    private Button deconnecterBtn;

    @FXML
    private Button enregistreBtn;


    @FXML
    private Button modifierBtn;


    @FXML
    private TextField moyenneTfd;


    @FXML
    private TextField nomTfd;


    @FXML
    private TextField prenomTfd;

    @FXML
    private Button supprimerBtn;


    @FXML
    private Button viderBtn;

    @FXML
    void classeO(ActionEvent event) throws IOException {
        /**
         * Cette fonction permet d'afficher le formulaire crud de classe
         */
        Outils.load(event, "CRUD", "/Fxml/classe.fxml");

    }

    @FXML
    void clear(ActionEvent event) {
        vider();
    }

    @FXML
    void deconnect(ActionEvent event) throws IOException {
        Notification.NotifError("Warning", "Vous n'etes plus connectés!");
        Outils.load(event, "CRUD", "/Fxml/login.fxml");
    }

    @FXML
    void delete(ActionEvent event) {
        if (iEtudiant.delete(id)==1){
            Notification.NotifSuccess("Succes","Suppression reussie!!");
            loadTable();
            vider();
        }else {
            Notification.NotifError("Error","Erreur de la suppression");
        }
    }

    @FXML
    void save(ActionEvent event) {
        String nom = nomTfd.getText();
        String prenom = prenomTfd.getText();
        Double moyenne = Double.valueOf(moyenneTfd.getText());
        String classe = classeCbx.getValue();
        System.out.println(nom);
        if (nom.equals("") || prenom.equals("") || classe == null)
            Notification.NotifError("Error", "Tous les champs sont obligatoires!");
        else {
            Etudiant e = new Etudiant(nom, prenom, moyenne, new ClasseImpl().getClasse(classe));
            if (iEtudiant.add(e) == 1) {
                Notification.NotifSuccess("Success", "Enregistrement reussi!");
                loadTable();
                vider();
            } else {
                Notification.NotifError("Error", "Echec de l'enregistrement");
            }
        }
    }

    @FXML
    void update(ActionEvent event) {
        /**
         * Mise a jour d'un etudiant
         */
        String nom = nomTfd.getText();
        String prenom = prenomTfd.getText();
        Double moyenne = Double.valueOf(moyenneTfd.getText());
        String classe = classeCbx.getValue();
        System.out.println(nom);
        if (nom.equals("") || prenom.equals("") || classe == null)
            Notification.NotifError("Error", "Tous les champs sont obligatoires!");
        else {
            Etudiant e = iEtudiant.get(id);
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setMoyenne(moyenne);
            e.setClasse(new ClasseImpl().getClasse(classe));
            e.setMatricule(e.generateMatricule());
            if (iEtudiant.update(e) == 1) {
                Notification.NotifSuccess("Success", "Enregistrement reussi!");
                loadTable();
                vider();
            } else {
                Notification.NotifError("Error", "Echec de l'enregistrement");
            }
        }
    }

    @FXML
    void charge(MouseEvent event) {
        /**
         * Chargement des donnees dans les champs
         */
        Etudiant e = tableEtudiant.getSelectionModel().getSelectedItem();
        nomTfd.setText(e.getNom());
        prenomTfd.setText(e.getPrenom());
        moyenneTfd.setText(String.valueOf(e.getMoyenne()));
        classeCbx.setValue(e.getC());
        id = e.getId();
        enregistreBtn.setDisable(true);
        modifierBtn.setDisable(false);
        supprimerBtn.setDisable(false);
    }


    public void initClasse() {
        /**
         * Initialisation du combobox avec les classes enregistres
         */
        for (Classe c : new ClasseImpl().list()) {
            classeCbx.getItems().add(c.getNom());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClasse();
        formatage();
        loadTable();
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);
    }

    private void loadTable() {
        /**
         * Cette methode permet de charger le tableau d'etudiants
         */
        ObservableList<Etudiant> etudiants = FXCollections.observableList(iEtudiant.list());
        tableEtudiant.setItems(etudiants);
        idC.setCellValueFactory(new PropertyValueFactory<Etudiant, Integer>("id"));
        MatriculeC.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("matricule"));
        nomC.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));
        prenomC.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("prenom"));
        moyenneC.setCellValueFactory(new PropertyValueFactory<Etudiant, Double>("moyenne"));
        classeC.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("c"));
    }

    private void vider() {
        /**
         * Cette fonction permet de vider les champs
         */
        nomTfd.setText("");
        prenomTfd.setText("");
        moyenneTfd.setText("0.0");
        classeCbx.setValue("");
        id = 0;
        enregistreBtn.setDisable(false);
        modifierBtn.setDisable(true);
        supprimerBtn.setDisable(true);
    }
    private void formatage(){
        /**
         * Cette fonction permet de formater le champ pour saisir que des reels
         */
        TextFormatter<Double> doubleFormatter = new TextFormatter<>(new DoubleStringConverter(), 0.0, change -> {
            String input = change.getControlNewText();
            if (input.matches("(\\d*\\.?\\d*)") && (Double.parseDouble(input) >= 0.0 && Double.parseDouble(input) <= 20.0) && input.length() <= 5) {
                return change;
            }
            return null;
        });
        moyenneTfd.setTextFormatter(doubleFormatter);
    }
}