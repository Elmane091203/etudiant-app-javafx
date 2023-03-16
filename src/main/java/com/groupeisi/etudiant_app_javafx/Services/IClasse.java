package com.groupeisi.etudiant_app_javafx.Services;

import com.groupeisi.etudiant_app_javafx.entity.Classe;

public interface IClasse extends Repository<Classe> {
    Classe getClasse(String classe);
}
