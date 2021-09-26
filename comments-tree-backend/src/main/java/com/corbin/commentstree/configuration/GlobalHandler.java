package com.corbin.commentstree.configuration;

import com.corbin.commentstree.bean.vo.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author corbin
 * controller的全局配置（统一异常处理等）
 */
@RestControllerAdvice
@Slf4j
public class GlobalHandler {

    /**
     * 处理参数校验失败的异常
     * @param e 异常对象
     * @return 统一响应对象
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult<Object> handleInvalidateParamException(ConstraintViolationException e){
        log.error("InvalidateParam", e);
        return RestResult.fail(e.getMessage());
    }

    /**
     * 通用的异常处理器,所有预料之外的异常都由这里处理
     * @param e 异常对象
     * @return 统一响应对象
     */
    @ExceptionHandler(Throwable.class)
    public RestResult<Object> handleOtherException(Throwable e) {
        log.error("application error", e);
        return RestResult.error();
    }
}
