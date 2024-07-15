package com.lectureservice.global.security.anotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target({PARAMETER})
@Retention(RUNTIME)
@AuthenticationPrincipal(expression = "userId")
public @interface CurrentUserId {
  boolean required() default false;
}
