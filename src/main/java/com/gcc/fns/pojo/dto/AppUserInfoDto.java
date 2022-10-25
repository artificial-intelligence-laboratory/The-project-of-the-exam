package com.gcc.fns.pojo.dto;

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
    private String nickname;

    /**
     * 用户头像地址
     */
    private String faceUrl;

    /**
     * 学院
     */
    @NotBlank(message = "学院不能为空")
    private String college;

    /**
     * 专业
     */
    @NotBlank(message = "专业不能为空")
    private String major;

    /**
     * 班级名
     */
    @NotBlank(message = "班级不能为空")
    private String className;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

}
