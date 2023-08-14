package com.saulpos.javafxcrudgenerator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface TableViewColumn {
    double minWidth() default 100;
    double maxWidth() default Integer.MAX_VALUE;
    double prefWidth() default 100;
}
