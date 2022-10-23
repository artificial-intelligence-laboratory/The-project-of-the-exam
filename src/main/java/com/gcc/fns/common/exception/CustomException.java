package com.gcc.fns.common.exception;

import com.gcc.fns.common.enums.ResponseStatusEnum;
import lombok.Data;

/**
 * @author xiaozhi
 * @description 自定义异常
 * @create 2022-10-2022/10/21 12:05
 */
@Data
public class CustomException extends RuntimeException{

    private ResponseStatusEnum responseStatusEnum;

    public CustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为：" + responseStatusEnum.status()
                + ";异常信息为：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }
}
