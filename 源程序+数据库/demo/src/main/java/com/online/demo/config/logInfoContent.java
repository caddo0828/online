package com.online.demo.config;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface logInfoContent {
    String content() default "log info content...";

    boolean isInsertLog() default true;
}
