package com.pkadung.quizzapp;

import com.pkadung.utils.MyStage;
import com.pkadung.utils.theme.Theme;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private ComboBox<Theme> cbThemes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbThemes.setItems(FXCollections.observableArrayList(Theme.values()));
    }

    public void changeTheme(ActionEvent event) {
        this.cbThemes.getSelectionModel().getSelectedItem().updateTheme(this.cbThemes.getScene());
    }

    public void handleQuestionManagement(ActionEvent action) throws IOException {
        MyStage.getInstance().showStage("question.fxml");
    }

    public void handlePractice(ActionEvent action) throws IOException {
        MyStage.getInstance().showStage("practice.fxml");
    }

}
