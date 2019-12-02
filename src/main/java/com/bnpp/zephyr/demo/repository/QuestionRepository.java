package com.bnpp.zephyr.demo.repository;

import com.bnpp.zephyr.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Modifying
  @Query("update Question q set q.title = :title where q.title = :title")
  int updateQuestionTitle(@Param("title") String title);
}
