package com.saulpos.javafxcrudgenerator.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target(FIELD)
public @interface Category {
    String defaultCategory = "Basic";

    String name() default defaultCategory;
}
