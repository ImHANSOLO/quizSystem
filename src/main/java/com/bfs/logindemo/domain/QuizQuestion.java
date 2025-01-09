package com.bfs.logindemo.domain;


public class QuizQuestion {
    private int qqId;
    private int quizId;
    private int questionId;
    private Integer userChoiceId;

    // Getters & Setters
    public int getQqId() { return qqId; }
    public void setQqId(int qqId) { this.qqId = qqId; }

    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public Integer getUserChoiceId() { return userChoiceId; }
    public void setUserChoiceId(Integer userChoiceId) { this.userChoiceId = userChoiceId; }
}