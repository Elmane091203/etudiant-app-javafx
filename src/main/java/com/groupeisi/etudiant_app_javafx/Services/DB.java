package com.groupeisi.etudiant_app_javafx.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    /**
     cnx : Pour la connexion
     pstm : Pour les requetes préparées
     rs :  Pour les requetes de consultations (SELECT)
     ok : Pour les requetes de mise à jour (INSERT INTO, UPDATE, DELETE)
     *
     */
    private Connection cnx;
    private PreparedStatement pstm;
    private ResultSet rs;
    private int ok;


    private void getConnection() {
        String url = "jdbc:mysql://localhost:3306/etudiant_app";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(url,user,password);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void initPrepar(String sql){
        try {
            getConnection();
            pstm = cnx.prepareStatement(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeSelect(){
        rs = null;
        try {
            rs = pstm.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int executeMaj(){
        try {
            ok = pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    public void closeConnection(){
        try {
            if (cnx !=null){
                cnx.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public PreparedStatement getPstm() {
        return pstm;
    }
}
