package com.bnpp.zephyr.demo.repository;

import static com.bnpp.zephyr.demo.fixture.QuestionFixture.buildQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.bnpp.zephyr.demo.util.CustomPostgresqlContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)//will only create spring beans and not any mock the servlet environment.

//The in-memory database and the database used in production behave differently and may return different results.
// So a green in-memory-database-based test is no guaranty for the correct behavior of your application in production.

@Transactional
public class QuestionRepositoryInMemoryIT {

  private static final String QUESTION_TITLE = "jdk11";

  @Autowired
  private QuestionRepository repository;

  @PersistenceContext
  EntityManager entityManager;


  @Before
  public void initUseCase() {
    System.setProperty("DB_URL", "jdbc:h2:mem:testdb");
    System.setProperty("DB_USERNAME", "sa");
    System.setProperty("DB_PASSWORD", "password");
  }

  @Test
  public void updateQuestionTitle_GivenQuestionsInDB_WhenUpdateTitle_ThenModifyMatchingQuestions() {
    //GIVEN:
    insertQuestions();

    //WHEN:
    int updatedQuestionsSize = repository.updateQuestionTitle(QUESTION_TITLE);

    //THEN:
    assertThat(updatedQuestionsSize).isEqualTo(2);

  }

  private void insertQuestions() {
    repository.save(buildQuestion(QUESTION_TITLE, "Question1?"));
    repository.save(buildQuestion(QUESTION_TITLE, "Question2?"));
    entityManager.flush();
  }

}