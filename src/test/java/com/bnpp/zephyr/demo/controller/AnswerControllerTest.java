package com.bnpp.zephyr.demo.controller;

import static com.bnpp.zephyr.demo.fixture.QuestionFixture.QUESTION_ID;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import com.bnpp.zephyr.demo.repository.AnswerRepository;
import com.bnpp.zephyr.demo.service.AnswerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * /* - Load only the web components of spring: - @Controller, - @ControllerAdvice, -
 * @JsonComponent, - Converter/GenericConverter, - Filter, - WebMvcConfigurer -
 * HandlerMethodArgumentResolver - It does NOT load: -  @Component, - @Service - @Repositor
 *
 * - The components that are not loaded needs to be mocked - You can use MockMvc to make requests
 * using relative URL's
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

  @Autowired
  // comes with WebMvcTest annotation
  private MockMvc mockMvc;

  @MockBean
  private AnswerService answerService;

  @MockBean
  private AnswerRepository answerRepository;


  @Test
  public void getAnswersByQuestionId() throws Exception {
    //GIEVN
    doReturn(getAnswerList(QUESTION_ID)).when(answerRepository).findByQuestionId(eq(QUESTION_ID));

    //WHEN:
    mockMvc.perform(get("/questions/" + QUESTION_ID + "/answers"))
        .andDo(print())

     //THEN :

        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));

    verify(answerRepository).findByQuestionId(eq(QUESTION_ID));
  }

  private static List<Answer> getAnswerList(Long questionId) {
    List<Answer> answerList = new ArrayList<>(2);
    Question question = new Question();
    question.setId(questionId);
    Answer answer1 = new Answer();
    answer1.setText("answer1");
    answer1.setQuestion(question);
    Answer answer2 = new Answer();
    answer2.setText("answer2");
    answer2.setQuestion(question);
    answerList.add(answer1);
    answerList.add(answer2);
    return answerList;

  }
}