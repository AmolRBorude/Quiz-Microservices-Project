package com.microconfig.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microconfig.example.Services.QuizService;
import com.microconfig.example.entities.Quiz;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController 
{

    private QuizService quizService;

    public QuizController(QuizService quizService) 
    {
        this.quizService = quizService;
    }

    //    create
    @PostMapping
    public Quiz create(@RequestBody Quiz quiz) 
    {
        return quizService.add(quiz);
    }

//    get all

    @GetMapping
    public List<Quiz> get() 
    {
        return quizService.get();
    }

    @GetMapping("/{id}")
    public Quiz getOne(@PathVariable Long id) 
    {
        return quizService.get(id);
    }


}
