package com.gcc.fns.common.enums;

/**
 * @author xiaozhi
 * @description 订单状态枚举
 * @create 2022-10-2022/10/26 23:02
 */
public enum OrderStatus {

    TO_BE_REVIEWED(0, "待审核"),
    TO_BE_RECEIVED(1, "代接取"),
    TO_BE_COMPLETED(2, "待结束"),
    CLOSED(3, "已结束"),
    TO_BE_ACCEPTED(4, "待验收");

    public final int code;
    public final String status;

    OrderStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
