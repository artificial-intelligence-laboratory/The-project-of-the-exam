package com.gcc.fns.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

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
}
