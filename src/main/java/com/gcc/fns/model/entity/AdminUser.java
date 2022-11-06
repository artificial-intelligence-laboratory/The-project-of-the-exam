package com.gcc.fns.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaozhi
 * @description 管理员用户
 * @create 2022-10-2022/10/21 12:25
 */
@TableName("t_admin_user")
@Data
public class AdminUser implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *管理员账户
     */
    private String account;

    /**
     *管理员密码
     */
    private String password;

    /**
     *盐
     */
    private String salt;

    /**
     *管理员类型 0-超级管理员 1-用户管理员 2-订单管理员
     */
    private  String adminType;

    /**
     *管理员账户状态 0-未激活 1-已激活
     */
    private Integer status;

    /**
     *创建时间
     */
    private Date createTime;


}
