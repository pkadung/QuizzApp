/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pkadung.quizzapp;

import com.pkadung.pojo.Category;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionController implements Initializable {
    @FXML private ComboBox<Category> cbCates;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quizdb", "root", "root");
            
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT *FROM category");
            
            List<Category> cates = new ArrayList<>();
            while (rs.next()){
               int id = rs.getInt("id");
               String name = rs.getString("name");
               
               Category c = new Category(id, name);
               cates.add(c);
            }
            
            conn.close();
            this.cbCates.setItems(FXCollections.observableList(cates));
        }
        catch (ClassNotFoundException |SQLException ex) {
            ex.printStackTrace();
        }
        
    }    
    
}
