package com.bfs.logindemo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer quizId;

    // references user_id
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // references category_id
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @Column(name="name")
    private String name;

    @Column(name="time_start")
    private LocalDateTime timeStart;

    @Column(name="time_end")
    private LocalDateTime timeEnd;

    // one quiz => many quizQuestions
    @OneToMany(mappedBy="quiz", cascade= CascadeType.ALL)
    private List<QuizQuestion> quizQuestions = new ArrayList<>();

    public Quiz() {}

    // getters / setters
    public Integer getQuizId() {
        return quizId;
    }
    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }
    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
}
