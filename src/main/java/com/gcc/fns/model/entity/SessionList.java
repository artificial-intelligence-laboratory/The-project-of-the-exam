package com.gcc.fns.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author snail
 * @create 2022-10-30  16:22
 */
@TableName("t_session_list")
@Data
public class SessionList implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 目标用户
     */
    private Long toUserId;

    /**
     * 会话名称
     */
    private String listName;

    /**
     * 未读消息数
     */
    private Integer unReadCount;

    /**
     *对方的头像
     */
    private String avatar;
}
