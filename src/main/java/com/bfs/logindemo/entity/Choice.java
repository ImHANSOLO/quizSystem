package com.bfs.logindemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "Choice")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Integer choiceId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    // reference to question
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Choice() {}

    public Integer getChoiceId() {
        return choiceId;
    }
    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
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

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
}
