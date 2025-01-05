package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Repository / DAO layer
// We are using some mocked (fake) data here.
// In your project, you need to use mySQL database, configure the data source.
@Repository
public class QuestionDao {
    private static final Question question;
    private static final List<Choice> choices;

    static {
        choices = new ArrayList<>();
        choices.add(new Choice(1, "42"));
        choices.add(new Choice(2, "correct answer"));
        choices.add(new Choice(3, "yes"));
        question = new Question(
                1,
                "What is the correct answer?",
                choices,
                2);
    }

    public Question getQuestion() {
        return question;
    }
}
