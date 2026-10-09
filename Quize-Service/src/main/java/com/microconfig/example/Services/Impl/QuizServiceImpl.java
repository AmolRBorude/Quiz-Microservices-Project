package com.microconfig.example.Services.Impl;



import java.util.List;

import org.springframework.stereotype.Service;

import com.microconfig.example.Services.QuizService;
import com.microconfig.example.entities.Quiz;
import com.microconfig.example.repositories.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz get(Long id) {
        return quizRepository.findById(id).orElse(null);
    }
}
