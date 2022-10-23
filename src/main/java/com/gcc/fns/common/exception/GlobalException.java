package com.gcc.fns.common.exception;

import com.gcc.fns.common.utils.GraceJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaozhi
 * @description 全局异常拦截处理
 * @create 2022-10-2022/10/21 12:12
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public GraceJSONResult returnErrorInfo(CustomException e) {
        return GraceJSONResult.errorCustom(e.getResponseStatusEnum());
    }



}
