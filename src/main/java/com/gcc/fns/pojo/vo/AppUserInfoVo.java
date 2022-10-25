package com.gcc.fns.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/25 18:41
 */
@Data
public class AppUserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String faceUrl;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级名
     */
    private String className;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;
}
