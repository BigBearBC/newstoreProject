package com.cy.store.commom.queryUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记排序
 *
 * @Author
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryOrder {

    //支持排序
    Order order() default Order.ASC;

    enum Order {
        //  升序
        ASC
        //  降序
        , DESC
    }
}

