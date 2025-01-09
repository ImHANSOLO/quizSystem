package com.bfs.logindemo.domain;
import java.time.LocalDateTime;

public class Quiz {
    private int quizId;
    private int userId;
    private int categoryId;
    private String name;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    // Getters & Setters
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalDateTime timeStart) { this.timeStart = timeStart; }

    public LocalDateTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalDateTime timeEnd) { this.timeEnd = timeEnd; }
}