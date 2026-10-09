package com.microconfig.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microconfig.example.entities.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizId(Long quizId);
}