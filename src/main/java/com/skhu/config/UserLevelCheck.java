package com.skhu.config;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.skhu.config.UserLevel.UNAUTH;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface UserLevelCheck {
    UserLevel level() default UNAUTH;
}