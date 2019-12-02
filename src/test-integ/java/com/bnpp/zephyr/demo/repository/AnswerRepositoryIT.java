package com.bnpp.zephyr.demo.repository;

import static com.bnpp.zephyr.demo.fixture.AnswerFixture.*;
import static com.bnpp.zephyr.demo.fixture.QuestionFixture.*;

import com.bnpp.zephyr.demo.AbstractIntegrationTest;
import com.bnpp.zephyr.demo.fixture.AnswerFixture;
import com.bnpp.zephyr.demo.fixture.QuestionFixture;
import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles("test")
@Transactional
public class AnswerRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private AnswerRepository repository;

  @PersistenceContext
  EntityManager entityManager;


  @Test
  public void findByQuestionId() {
    //GIVEN:
    Question question = questionRepository.save(buildQuestion("Spring Boot interview questions",
            "What embedded containers does Spring Boot support?"));
    entityManager.persist(buildAnswer(question,
        "Spring Boot support three embedded containers: Tomcat, Jetty, and Undertow. By default, it uses Tomcat as embedded containers but you can change it to Jetty or Undertow."));

    //WHEN:
    List<Answer> answers = repository.findByQuestionId(question.getId());

    //THEN:
    Assertions.assertThat(answers).hasSize(1);
  }
}