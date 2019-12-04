package com.bnpp.zephyr.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.test.annotation.IfProfileValue;

/*
  mvn -Dtestprofile=integrationtest
 */
@Retention(RetentionPolicy.RUNTIME)
@IfProfileValue(name = "testprofile", value = "integrationtest")
public @interface IntegrationTest {

}