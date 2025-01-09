package com.bfs.logindemo.domain;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private int questionId;
    private int categoryId;
    private String description;
    private boolean active;

    // For front-end display
    private List<Choice> choices; // the possible answers

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Choice> getChoices() {
        return choices;
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
