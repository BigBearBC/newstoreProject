package com.cy.store.commom.queryUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是否驼峰转下划线
 *
 * @Author
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryToUnderLine {

    //支持驼峰转下划线
    boolean toUnderLine() default true;

}

