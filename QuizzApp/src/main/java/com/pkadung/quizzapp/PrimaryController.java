package com.pkadung.quizzapp;

import com.pkadung.utils.MyAlert;
import com.pkadung.utils.MyStage;
import com.pkadung.utils.theme.DefaultThemeFactory;
import com.pkadung.utils.theme.Theme;
import com.pkadung.utils.theme.ThemeManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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

    public void handlePractice(ActionEvent action) {
        MyAlert.getInstance().showMsg("Coming soon...");
    }

}
