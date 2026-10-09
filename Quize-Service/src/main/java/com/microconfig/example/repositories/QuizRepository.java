package com.microconfig.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microconfig.example.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
