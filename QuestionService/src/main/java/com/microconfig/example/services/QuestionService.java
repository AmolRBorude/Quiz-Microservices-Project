package com.microconfig.example.services;

import java.util.List;

import com.microconfig.example.entities.Question;

public interface QuestionService {

    Question create(Question question);
    List<Question> get();
    Question getOne(Long id);

    List<Question> getQuestionsOfQuiz(Long quizId);
}