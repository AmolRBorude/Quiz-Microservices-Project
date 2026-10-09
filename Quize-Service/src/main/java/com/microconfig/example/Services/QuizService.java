package com.microconfig.example.Services;

import java.util.List;

import com.microconfig.example.entities.Quiz;

public interface QuizService {

    Quiz add(Quiz quiz);

    List<Quiz> get();

    Quiz get(Long id);

}