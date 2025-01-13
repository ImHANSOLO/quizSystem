package com.bfs.logindemo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
//@Data
@Table(name = "QuizQuestion")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qq_id")
    private Integer qqId;

    @ManyToOne
    @JoinColumn(name="quiz_id", nullable=false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    // user_choice_id => references Choice
    @ManyToOne
    @JoinColumn(name="user_choice_id")
    private Choice userChoice; // can be null

    public QuizQuestion() {}

    // getters / setters
    public Integer getQqId() {
        return qqId;
    }
    public void setQqId(Integer qqId) {
        this.qqId = qqId;
    }

    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }

    public Choice getUserChoice() {
        return userChoice;
    }
    public void setUserChoice(Choice userChoice) {
        this.userChoice = userChoice;
    }
}
