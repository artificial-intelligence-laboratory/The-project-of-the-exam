package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaozhi
 * @description 验证码登录请求体
 * @create 2022-10-2022/10/24 23:16
 */
@Data
public class SendMailRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @Email(message = "邮箱格式不正确，请重新输入")
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
