package com.bfs.logindemo.domain;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int id;
    private String description;
    private List<Choice> choices;
    private int correctChoiceId;
}
