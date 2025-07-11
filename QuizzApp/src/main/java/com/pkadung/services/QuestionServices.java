/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pkadung.services;

import com.pkadung.pojo.Choice;
import com.pkadung.pojo.Question;
import com.pkadung.utils.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class QuestionServices {

    public void addQuestion(Question q) throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();

        conn.setAutoCommit(false);
        String sql = "INSERT INTO question(content, hint, image, category_id, level_id) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setString(1, q.getContent());
        stm.setString(2, q.getHint());
        stm.setString(3, q.getImage());
        stm.setInt(4, q.getCategory().getId());
        stm.setInt(5, q.getLevel().getId());

        if (stm.executeUpdate() > 0) {
            int qId = -1;
            ResultSet r = stm.getGeneratedKeys();
            if (r.next()) {
                qId = r.getInt(1);
            }

            sql = "INSERT INTO choice(content, is_correct, question_id) VALUES(?, ?, ?)";

            for (var c : q.getChoices()) {
                stm = conn.prepareCall(sql);
                stm.setString(1, c.getContent());
                stm.setBoolean(2, c.isCorrect());
                stm.setInt(3, qId);

                stm.executeUpdate();
            }

            conn.commit();
        } else {
            conn.rollback();
        }
    }

    public List<Question> getQuestions() throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM question");

        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = rs.getString("content");
            System.out.println(id);
            Question q = new Question.Builder(id, content).build();
            questions.add(q);
        }

        return questions;
    }

    public List<Question> getQuestions(String kw) throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();
        String sql = "Select * from question where content like concat('%', ?, '%')";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = rs.getString("content");
            System.out.println(id);
            Question q = new Question.Builder(id, content).build();
            questions.add(q);
        }

        return questions;
    }

    public List<Question> getQuestions(int num) throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();
        String sql = "Select * from question Order By rand() Limit ?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, num);
        ResultSet rs = stm.executeQuery();

        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = rs.getString("content");
            System.out.println(id);
            Question q = new Question.Builder(id, content).addAllChoice(this.getChoicesByQuestionId(id)).build();


            questions.add(q);
        }

        return questions;
    }

    public List<Choice> getChoicesByQuestionId(int questionId) throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();

        String sql = "Select * from choice where question_id=?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, questionId);

        ResultSet rs = stm.executeQuery();

        List<Choice> choices = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = rs.getString("content");
            boolean correct = rs.getBoolean("is_correct");

            choices.add(new Choice(id, content, correct));
        }

        return choices ;
    }


    public boolean deleteQuestion(int questionId) throws SQLException {
        Connection conn = JdbcConnector.getInstance().connect();
        String sql = "Delete From question Where id=?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, questionId);
        if(stm.executeUpdate() > 0)
        {
            return true;
        }
        return false;
    }

}
