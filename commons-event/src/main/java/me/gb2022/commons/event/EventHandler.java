package me.gb2022.commons.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    int priority() default 0;

    boolean ignoreCancel() default false;
}
