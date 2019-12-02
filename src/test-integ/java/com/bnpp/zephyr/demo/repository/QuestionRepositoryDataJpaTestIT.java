package com.bnpp.zephyr.demo.repository;

import static com.bnpp.zephyr.demo.fixture.QuestionFixture.buildQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.bnpp.zephyr.demo.config.JpaAuditingConfig;
import com.bnpp.zephyr.demo.util.CustomPostgresqlContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class QuestionRepositoryDataJpaTestIT {

  private static final String QUESTION_TITLE = "jdk11";

  @Autowired
  private QuestionRepository repository;

  @PersistenceContext
  EntityManager entityManager;

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = CustomPostgresqlContainer.getInstance();


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