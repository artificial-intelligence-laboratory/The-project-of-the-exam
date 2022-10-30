package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/25 18:41
 */
@Data
public class AppUserInfoDto implements Serializable {

    private static final long serialVersionUID = 319124171233120793L;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String username;

    /**
     * 用户头像地址
     */
    @NotBlank(message = "头像不能为空")
    private String avatar;

    /**
     * 学院
     */
    @NotBlank(message = "学院不能为空")
    private String academy;

    /**
     * 专业
     */
    @NotBlank(message = "专业不能为空")
    private String specialities;

    /**
     * 班级名
     */
    @NotBlank(message = "班级不能为空")
    private String classes;

    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    private String num;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

}
