package com.bnpp.zephyr.demo.controller;

import static com.bnpp.zephyr.demo.fixture.AnswerFixture.buildAnswer;
import static com.bnpp.zephyr.demo.fixture.QuestionFixture.buildQuestion;
import static com.jayway.jsonpath.JsonPath.parse;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.repository.QuestionRepository;
import com.bnpp.zephyr.demo.util.CustomPostgresqlContainer;
import com.jayway.jsonpath.DocumentContext;
import io.restassured.response.ResponseOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

/*
 - Load complete spring environment but without the Servlet container
 - You can mock any bean in the system, using @MockBean
 - You can inject mockMvc to make requests to the application using relative URL's
 - You cannot create real rest call's, but you can use MockMvc to make requests

 Note:
 when use MockMvc,it might be better te use WebEnvironment.MOCK
 or use @WebMvcTest

 En debug, pas de request http : .TestDispatcherServlet - Completed 200 OK, headers={masked}
 MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /questions/2051/answers
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc  // this is needed to autowire the MockMvc
public class AnswerControllerWithEnvMockIT {

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private AnswerRepository answerRepository;

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = CustomPostgresqlContainer.getInstance();


  @Autowired
  // comes with WebMvcTest annotation
  private MockMvc mockMvc;

  private RestTemplate restTemplate = new RestTemplate();

  @Test
  public void getAnswersByQuestionId_ShouldReturnAnswerList() throws Exception {
    //GIVEN
    Long questionId = insertQuestionAndAnswer();

    //WHEN:
    ResultActions resultActions = mockMvc.perform(get("/questions/" + questionId + "/answers"))
        .andDo(print())

        //THEN :

        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)));

    assertThat(resultActions).isNotNull();

  }


  @Test(expected = IllegalArgumentException.class)
  public void getAnswersByQuestionId_ShouldThrowsException() {

    //GIVEN
    Long questionId = insertQuestionAndAnswer();

    //WHEN:
    ResponseEntity<Answer[]> response =
        restTemplate.getForEntity(
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