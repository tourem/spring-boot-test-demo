package com.bnpp.zephyr.demo.fixture;

import com.bnpp.zephyr.demo.model.Question;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QuestionFixture {

  public Long QUESTION_ID = 1L;

  public Question buildQuestion(String title, String description) {
     return new Question().setTitle(title).setDescription(description);
  }

}
