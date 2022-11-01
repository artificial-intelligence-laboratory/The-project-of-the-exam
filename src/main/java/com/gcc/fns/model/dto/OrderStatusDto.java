package com.gcc.fns.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaozhi
 * @description 查询订单状态接收参数
 * @create 2022-10-2022/10/31 9:33
 */
@Data
public class OrderStatusDto implements Serializable {

    private static final long serialVersionUID = 319122316373120793L;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 查询类型类型，true为发单，false为接单
     */
    private Boolean isIssue;
}
