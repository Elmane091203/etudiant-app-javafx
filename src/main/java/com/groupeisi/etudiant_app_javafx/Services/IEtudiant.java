package com.groupeisi.etudiant_app_javafx.Services;

import com.groupeisi.etudiant_app_javafx.entity.Etudiant;

import java.util.List;

public interface IEtudiant extends Repository<Etudiant> {
    List<Etudiant> getEtudiantByClasse(String classe);
}
