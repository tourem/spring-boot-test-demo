package com.bnpp.zephyr.demo.service;

import com.bnpp.zephyr.demo.exception.ResourceNotFoundException;
import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.repository.QuestionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnswerService {

  private AnswerRepository answerRepository;

  private QuestionRepository questionRepository;

  @Autowired
  public AnswerService(AnswerRepository answerRepository,
      QuestionRepository questionRepository) {
    this.answerRepository = answerRepository;
    this.questionRepository = questionRepository;
  }

  public Answer addAnswer(Long questionId, Answer answer) {
    return questionRepository.findById(questionId)
        .map(question -> {
          answer.setQuestion(question);
          return answerRepository.save(answer);
        }).orElseThrow(
            () -> new ResourceNotFoundException("Question not found with id " + questionId));
  }

  public Answer updateAnswer(Long questionId, Long answerId, String answerText) {
    if (!questionRepository.existsById(questionId)) {
      throw new ResourceNotFoundException("Question not found with id " + questionId);
    }

    return answerRepository.findById(answerId)
        .map(answer -> {
          answer.setText(answerText);
          return answerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
  }

  public void deleteAnswer(Long questionId, Long answerId) {
    if (!questionRepository.existsById(questionId)) {
      throw new ResourceNotFoundException("Question not found with id " + questionId);
    }

    Optional<Answer> answerOptional = answerRepository.findById(answerId);
    if (!answerOptional.isPresent()) {
      throw new ResourceNotFoundException("Answer not found with id " + answerId);
    }
    answerRepository.delete(answerOptional.get());
  }
}
