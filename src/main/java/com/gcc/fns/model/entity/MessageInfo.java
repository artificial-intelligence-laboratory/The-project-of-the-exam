package com.gcc.fns.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author snail
 * @create 2022-10-29  16:08
 */
@TableName("t_message")
@Data
public class MessageInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送id
     */
    private Long fromUserId;

    /**
     * 接受者id
     */
    private  Long toUserId;


    /**
     * 通信内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    /**
     * 查看状态  0未读  1已读
     */
    private Integer unReadState;

//    /**
//     *头像
//     */
//    private String avatar;


}


