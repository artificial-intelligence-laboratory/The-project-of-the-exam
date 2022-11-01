package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/25 16:32
 */
@Data
public class PwdLoginRequest implements Serializable {

    private static final long serialVersionUID = 319122316373120793L;

    @Email(message = "邮箱格式不正确，请重新输入")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
