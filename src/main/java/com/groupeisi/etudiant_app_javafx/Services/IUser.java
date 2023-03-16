package com.groupeisi.etudiant_app_javafx.Services;

import com.groupeisi.etudiant_app_javafx.entity.User;

public interface IUser {
    User seConnecter(String email, String password);

}
