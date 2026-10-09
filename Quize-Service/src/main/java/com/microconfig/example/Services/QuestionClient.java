package com.microconfig.example.Services;

import java.util.List;

import com.microconfig.example.entities.Question;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8082",value = "Question-Client")
@FeignClient(name = "QUESTION-SERVICE")
public interface QuestionClient {

//

  @GetMapping("/question/quiz/{quizId}")
  List<Question> getQuestionOfQuiz(@PathVariable Long quizId);
}