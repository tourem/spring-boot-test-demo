package com.bnpp.zephyr.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.bnpp.zephyr.demo.fixture.AnswerFixture;
import com.bnpp.zephyr.demo.fixture.QuestionFixture;
import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
//disables full auto-configuration and applies only configuration relevant to JPA tests.
// By default, tests annotated with @DataJpaTest use an embedded in-memory database and @Transactional
public class AnswerRepositoryTest {

  @Autowired
  private AnswerRepository repository;

  // comes with DataJpaTest annotation
  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void findByQuestionId() {
    //GIVEN:
    Question question = entityManager.persist(QuestionFixture
        .buildQuestion("Spring Boot interview questions",
            "What embedded containers does Spring Boot support?"));
    entityManager.persist(AnswerFixture.buildAnswer(question,
        "Spring Boot support three embedded containers: Tomcat, Jetty, and Undertow. By default, it uses Tomcat as embedded containers but you can change it to Jetty or Undertow."));

    //WHEN:
    List<Answer> answers = repository.findByQuestionId(question.getId());

    //THEN:
    assertThat(answers).hasSize(1);
  }
}