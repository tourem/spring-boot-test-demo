package com.bnpp.zephyr.demo.controller;

import static com.bnpp.zephyr.demo.fixture.AnswerFixture.buildAnswer;
import static com.bnpp.zephyr.demo.fixture.QuestionFixture.buildQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.repository.QuestionRepository;
import com.bnpp.zephyr.demo.util.CustomPostgresqlContainer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

/*


 - Load complete spring environment, including the Servlet container
 - You can mock any bean in the system, using @MockBean
 - With the Autowired @WebApplicationContext, you can configure RestAssuredMockMvc
 - In your tests you can use RestAssuredMockMvc if you like to use the RestAssured syntax


02-12-2019 11:39:18.068 [main] INFO  o.a.catalina.core.StandardEngine.log - Starting Servlet engine: [Apache Tomcat/9.0.27]

Une request http : .HttpURLConnection - sun.net.www.MessageHeader@276b68af4 pairs: {null: HTTP/1.1 200}{Content-Type: application/json}{Transfer-Encoding:
o.s.web.client.RestTemplate - Response 200 OK
o.s.web.client.RestTemplate - Reading to [com.bnpp.zephyr.demo.model.Answer[]]

 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AnswerControllerIT {

  @LocalServerPort
  private int localPort;

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private TestRestTemplate restTemplate;

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = CustomPostgresqlContainer.getInstance();


  @Test
  public void getAnswersByQuestionId_ShouldReturnAnswerList_withRestTemplate() {

    //GIVEN
    Long questionId = insertQuestionAndAnswer();

    //WHEN:
    ResponseEntity<Answer[]> response =
        restTemplate.getForEntity("http://localhost:" + localPort +
                "/questions/" + questionId + "/answers",
            Answer[].class);

    //THEN:
    assertThat(response.getBody()).hasSize(1);
  }

  private Long insertQuestionAndAnswer() {
    Question question = questionRepository.save(buildQuestion("Spring Boot interview questions",
        "What embedded containers does Spring Boot support?"));
    answerRepository.save(buildAnswer(question,
        "Spring Boot support three embedded containers: Tomcat, Jetty, and Undertow. By default, it uses Tomcat as embedded containers but you can change it to Jetty or Undertow."));
    return question.getId();
  }
}