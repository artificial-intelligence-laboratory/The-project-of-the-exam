package com.gcc.fns.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaozhi
 * @description 订单缩略信息返回类
 * @create 2022-10-2022/10/30 16:28
 */
@Data
public class OrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标题
     */
    private String title;


    /**
     * 工作地点
     */
    private String workPlace;

    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;


    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

}
