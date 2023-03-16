package com.groupeisi.etudiant_app_javafx.Services;

import java.util.List;

public interface Repository<T> {
    int add(T t);
    int update(T t);
    int delete(int id);
    List<T> list();
    T get(int id);
}
