package com.bnpp.zephyr.demo.controller;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.service.AnswerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

  @Autowired
  private AnswerService answerService;

  @Autowired
  private AnswerRepository answerRepository;


  @GetMapping("/questions/{questionId}/answers")
  public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
    return answerRepository.findByQuestionId(questionId);
  }

  @PostMapping("/questions/{questionId}/answers")
  public Answer createAnswer(@PathVariable Long questionId,
      @Valid @RequestBody Answer answer) {
    return answerService.addAnswer(questionId, answer);
  }

  @PutMapping("/questions/{questionId}/answers/{answerId}")
  public Answer updateAnswer(@PathVariable Long questionId,
      @PathVariable Long answerId,
      @Valid @RequestBody Answer answerRequest) {
    return answerService.updateAnswer(questionId, answerId, answerRequest.getText());
  }

  @DeleteMapping("/questions/{questionId}/answers/{answerId}")
  public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId,
      @PathVariable Long answerId) {
    answerService.deleteAnswer(questionId, answerId);
    return ResponseEntity.ok().build();
  }
}
