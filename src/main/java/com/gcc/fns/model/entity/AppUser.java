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
 * @description App用户
 * @create 2022-10-2022/10/24 20:29
 */
@TableName("t_app_user")
@Data
public class AppUser implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 昵称
     */
    private String username;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 学院
     */
    private String academy;

    /**
     * 专业
     */
    private String specialities;

    /**
     * 班级名
     */
    private String classes;

    /**
     * 学号
     */
    private String num;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态：0-未激活 1-已激活 2-禁用
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
