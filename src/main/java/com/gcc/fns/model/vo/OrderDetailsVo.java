package com.gcc.fns.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaozhi
 * @description 订单详细信息返回类
 * @create 2022-10-2022/10/26 14:59
 */
@TableName("t_order")
@Data
public class OrderDetailsVo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 使用雪花算法生成主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单用户：发单用户和接单用户
     */
    private AppUserInfoVo orderUser;

    /**
     * 标题
     */
    private String title;

    /**
     * 详情
     */
    private String details;

    /**
     * 注意事项
     */
    private String beCareful;

    /**
     * 工作地点
     */
    private String workPlace;

    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 订单类型
     */
    private String type;

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 订单结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date closedTime;

}
