package com.pkadung.quizzapp;

import com.pkadung.pojo.Question;
import com.pkadung.services.QuestionServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PracticeController implements Initializable {
    @FXML private Text txtContent;
    @FXML private TextField txtNum;
    @FXML private VBox vboxChoices;
    @FXML private Text txtResult;

    private List<Question> questions;
    private int currentQuestion = 0;

    private final static QuestionServices questionServerices = new QuestionServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleStart(ActionEvent event) {
        try {
            this.questions = questionServerices.getQuestions(Integer.parseInt(this.txtNum.getText()));
            this.loadQuestions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleNext(ActionEvent event) {
        if (currentQuestion < this.questions.size() - 1) {
            this.currentQuestion++;
            this.loadQuestions();
        }
    }

    public void handleCheck(ActionEvent event) {
        Question q = this.questions.get(currentQuestion);

        this.txtResult.getStyleClass().clear();
        for (int i = 0; i < q.getChoices().size(); i++) {
            if (q.getChoices().get(i).isCorrect()){
                RadioButton r = (RadioButton) this.vboxChoices.getChildren().get(i);
                if (r.isSelected()) {
                    this.txtResult.setText("Correct");
                    this.txtResult.getStyleClass().add("Correct");
                }
                else {
                    this.txtResult.setText("Incorrect");
                    this.txtResult.getStyleClass().add("Incorrect");
                }
            }
        }
    }

    private void loadQuestions() {
        Question q = this.questions.get(this.currentQuestion);

        this.txtContent.setText(q.getContent());

        vboxChoices.getChildren().clear();

        ToggleGroup g = new ToggleGroup();

        for (var c : q.getChoices()) {
            RadioButton r = new RadioButton(c.getContent());
            r.setToggleGroup(g);
            vboxChoices.getChildren().add(r);
        }
    }
}
