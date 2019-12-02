package com.bnpp.zephyr.demo.service;

import static com.bnpp.zephyr.demo.fixture.QuestionFixture.buildQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.repository.QuestionRepository;
import com.bnpp.zephyr.demo.util.CustomPostgresqlContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Transactional
//will only create spring beans and not any mock the servlet environment.
public class AnswerServiceIT {

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @PersistenceContext
  EntityManager entityManager;

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = CustomPostgresqlContainer.getInstance();

  private AnswerService answerService;

  @Before
  public void initUseCase() {
    answerService = new AnswerService(answerRepository, questionRepository);
  }

  @Test
  public void addAnswer_shouldAddAnswer_whenQuestionExists() {

    //GIVEN:
    Long questionId = insertQuestion();

    //WHEN:
    Answer actualAnswer = new Answer();
    actualAnswer.setText("answer");
    Answer expectedAnswer = answerService.addAnswer(questionId, actualAnswer);

    //THEN:
    assertThat(expectedAnswer).isNotNull();
    assertThat(expectedAnswer.getQuestion()).isNotNull();

  }

  private Long insertQuestion() {
    Question question = questionRepository.save(buildQuestion("title", "Question1?"));
    entityManager.flush();
    return question.getId();
  }
}