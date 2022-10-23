package com.gcc.fns.common.exception;

import com.gcc.fns.common.enums.ResponseStatusEnum;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/21 12:06
 */
public class ThrowException {

    /**
     * 抛出自定义异常
     * @param responseStatusEnum
     */
    public static void custom(ResponseStatusEnum responseStatusEnum) {
        throw new CustomException(responseStatusEnum);
    }
}
