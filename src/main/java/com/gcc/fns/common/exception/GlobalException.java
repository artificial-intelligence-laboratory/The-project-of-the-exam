package com.gcc.fns.common.exception;

import com.gcc.fns.common.enums.ResponseStatusEnum;
import com.gcc.fns.common.utils.GraceJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;


/**
 * @author xiaozhi
 * @description 全局异常拦截处理
 * @create 2022-10-2022/10/21 12:12
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public GraceJSONResult returnErrorInfo(CustomException e) {
        return GraceJSONResult.errorCustom(e.getResponseStatusEnum());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public GraceJSONResult returnErrorInfo(BindException e) {
        String message = e.getFieldErrors().get(0).getDefaultMessage();
        log.error(e.getMessage(), e);
        return GraceJSONResult.errorMsg(message);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GraceJSONResult returnErrorInfo(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        log.error(e.getMessage(), e);
        return GraceJSONResult.errorMsg(message);
    }

    /**
     * 文件超过指定大小异常
     */
    @ExceptionHandler({SizeLimitExceededException.class})
    public GraceJSONResult getSizeOverError(SizeLimitExceededException e) {
        log.error(e.getMessage(), e);
        return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_MAX_SIZE_ERROR);
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler({RuntimeException.class})
    public GraceJSONResult getSizeOverError(RuntimeException e) {
        log.error("服务器异常：{}", e.getMessage());
        return GraceJSONResult.errorMsg("服务器异常，请稍后重试~~~");
    }

}
