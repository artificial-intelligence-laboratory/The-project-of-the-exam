package com.gcc.fns.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xiaozhi
 * @description
 * @create 2022-11-2022/11/6 11:01
 */
@Data
public class UpdatePwdRequest {

    @NotBlank(message = "密码不能为空")
    private String password;
}
