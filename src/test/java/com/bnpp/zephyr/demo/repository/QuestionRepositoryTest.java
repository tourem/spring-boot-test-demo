package com.bnpp.zephyr.demo.repository;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionRepositoryTest {

  @Autowired
  private QuestionRepository repository;

  // comes with DataJpaTest annotation
  @Autowired
  private TestEntityManager entityManager;


}