package com.cy.store.commom;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 在插入数据时，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]..");
        log.info(metaObject.toString());
        metaObject.setValue("createdUser",BaseContext.getThreadLocal());
        metaObject.setValue("modifiedUser",BaseContext.getThreadLocal());
        metaObject.setValue("createdTime", LocalDateTime.now());
        metaObject.setValue("modifiedTime",LocalDateTime.now());
    }

    /**
     * 在更新数据时，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]..");
        log.info(metaObject.toString());
        metaObject.setValue("modifiedUser",BaseContext.getThreadLocal());
        metaObject.setValue("modifiedTime",LocalDateTime.now());
    }
}
