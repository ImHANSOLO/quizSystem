package com.bfs.logindemo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Choice {
    private int choiceId;
    private int questionId;
    private String description;
    private boolean isCorrect;

    // Getters & Setters
    public int getChoiceId() { return choiceId; }
    public void setChoiceId(int choiceId) { this.choiceId = choiceId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}