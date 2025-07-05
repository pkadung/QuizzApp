/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.pkadung.quizzapp;

import com.pkadung.pojo.Category;
import com.pkadung.pojo.Level;
import com.pkadung.pojo.Question;
import com.pkadung.services.CategoryServices;
import com.pkadung.services.LevelServices;
import com.pkadung.services.QuestionServices;
import com.pkadung.utils.MyAlert;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private ComboBox<Level> cbLevels;
    @FXML
    private TableView<Question> tbQuestions;
    @FXML
    private TextField txtSearch;
    /**
     * Initializes the controller class.
     */
    private static final CategoryServices cateServices = new CategoryServices();
    private static final LevelServices levelServices = new LevelServices();
    private static final QuestionServices questionServices = new QuestionServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCates.setItems(FXCollections.observableList(cateServices.getCates()));
            this.cbLevels.setItems(FXCollections.observableList(levelServices.getLevels()));

            this.loadColumns();
            this.loadQuestions(questionServices.getQuestions());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        this.txtSearch.textProperty().addListener((e) -> {
            try {
                this.loadQuestions(questionServices.getQuestions(this.txtSearch.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    private void loadQuestions(List<Question> questions) {
        this.tbQuestions.setItems(FXCollections.observableArrayList(questions));
    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colContent = new TableColumn("Noi dung cau hoi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(80);

        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(e -> {
            TableCell cell = new TableCell();

            Button btn = new Button("Xoa");
            btn.setOnAction(event -> {
                Optional<ButtonType> t = MyAlert.getInstance().showMsg("Xoa cau hoi se xoa luon cac lua chon ban co chac chan xoa khong?", Alert.AlertType.CONFIRMATION);
                if (t.isPresent() && t.get().equals(ButtonType.OK)) {
                    Question q = (Question) cell.getTableRow().getItem();
                    try {
                        if (questionServices.deleteQuestion(q.getId()) == true) {
                            MyAlert.getInstance().showMsg("Xoa thanh cong");
                            this.tbQuestions.getItems().remove(q);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(QuestionController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            });

            cell.setGraphic(btn);
            return cell;
        });

        this.tbQuestions.getColumns()
                .addAll(colId, colContent, colAction);
    }
}
