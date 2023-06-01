package com.cy.store.config.Exception;

import com.cy.store.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 */
//ControllerAdvice指明需要拦截的哪些类中的异常
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobeExceptionHandler {

    /**
     * 异常处理方法
     *
     * @param ex
     * @return
     */
    //拦截业务异常
    @ExceptionHandler(CustomException.class)
    public Result exceptionHandler(CustomException ex) {
        log.info(ex.getMessage());
        return Result.error(ex.getErrorMsg());
    }

}
