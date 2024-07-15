package com.lectureservice.global.security.anotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.lectureservice.domain.user.type.Role.Authority;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.security.access.annotation.Secured;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Secured(Authority.ADMIN)
public @interface RoleAdmin {

}
