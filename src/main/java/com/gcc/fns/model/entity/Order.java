package com.gcc.fns.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaozhi
 * @description 订单类
 * @create 2022-10-2022/10/26 14:59
 */
@TableName("t_order")
@Data
public class Order implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 使用雪花算法生成主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建订单用户
     */
    private AppUser fromUser;

    /**
     * 接单用户
     */
    private AppUser targetUser;

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
    private OrderType orderType;

    /**
     * 状态：0-待审核 1-代接取 2-待结束 3-已结束 4-待验收
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
