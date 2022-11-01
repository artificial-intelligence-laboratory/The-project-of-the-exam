package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/31 11:06
 */
@Data
public class UpdateOrderStatusDto implements Serializable {

    private static final long serialVersionUID = 319122236373120793L;

    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "订单状态不能为空")
    private Integer status;

}
