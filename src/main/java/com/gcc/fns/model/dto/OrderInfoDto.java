package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaozhi
 * @description 接收订单信息
 * @create 2022-10-2022/10/31 9:55
 */
@Data
public class OrderInfoDto implements Serializable {

    private static final long serialVersionUID = 319122316373120793L;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 详情
     */
    @NotBlank(message = "详情不能为空")
    private String details;

    /**
     * 注意事项
     */
    private String beCareful;

    /**
     * 工作地点
     */
    @NotBlank(message = "工作地点不能为空")
    private String workPlace;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private Double fee;

    /**
     * 订单类型
     */
    @Min(value = 0, message = "请选择正确的订单类型")
    @Max(value = 4, message = "请选择正确的订单类型")
    @NotNull(message = "订单类型不能为空")
    private Integer typeId;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Date endTime;
}
