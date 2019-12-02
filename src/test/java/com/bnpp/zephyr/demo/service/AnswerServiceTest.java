package com.bnpp.zephyr.demo.service;

import static com.bnpp.zephyr.demo.fixture.QuestionFixture.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.bnpp.zephyr.demo.exception.ResourceNotFoundException;
import com.bnpp.zephyr.demo.fixture.QuestionFixture;
import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceTest {

  @Mock
  private AnswerRepository answerRepository;

  @Mock
  private QuestionRepository questionRepository;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  private AnswerService answerService;

  @Before
  public void initUseCase() {
    answerService = new AnswerService(answerRepository, questionRepository);
  }

  @Test
  public void addAnswer_shouldReturnResourceNotFoundException_whenAQuestionDoesNotExists() {

    //THEN:
    exceptionRule.expect(ResourceNotFoundException.class);
    exceptionRule.expectMessage("Question not found with id 1");

    //GIVEN:
    doReturn(false).when(questionRepository).existsById(eq(QUESTION_ID));

    //WHEN:
    answerService.addAnswer(QUESTION_ID, new Answer());

  }
}