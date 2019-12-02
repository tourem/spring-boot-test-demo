package com.bnpp.zephyr.demo.fixture;

import com.bnpp.zephyr.demo.model.Answer;
import com.bnpp.zephyr.demo.model.Question;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnswerFixture {

  public Answer buildAnswer(Question question, String text) {
     return new Answer().setQuestion(question).setText(text);
  }

}
