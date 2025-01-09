package com.bfs.logindemo.domain;

import java.io.Serializable;

public class QuizQuestion implements Serializable {
    private int qqId;
    private int quizId;
    private int questionId;
    private Integer userChoiceId;  // which choice user picked

    // For front-end display: the entire Question object (includes description + choice list)
    private Question question;

    public int getQqId() {
        return qqId;
    }
    public void setQqId(int qqId) {
        this.qqId = qqId;
    }

    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Integer getUserChoiceId() {
        return userChoiceId;
    }
    public void setUserChoiceId(Integer userChoiceId) {
        this.userChoiceId = userChoiceId;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
}
