package com.bfs.logindemo.domain;

import java.io.Serializable;

public class Choice implements Serializable {
    private int choiceId;
    private int questionId;
    private String description;
    private boolean correct;

    public int getChoiceId() {
        return choiceId;
    }
    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
