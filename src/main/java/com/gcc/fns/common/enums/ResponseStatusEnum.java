package com.gcc.fns.common.enums;

/**
 * @author xiaozhi
 * @description 结果状态码和信息封装
 * @create 2022-10-2022/10/21 12:01
 */
public enum ResponseStatusEnum {
    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    /* 用户相关 */
    UN_LOGIN(501,false,"请登录后再继续操作！"),
    TICKET_INVALID(502,false,"会话失效，请重新登录！"),
    USER_INACTIVE_ERROR(509,false,"请前往[账号设置]修改信息激活后再进行后续操作！"),
    USER_FROZEN_ERROR(522,false,"用户被冻结，请联系管理员解冻"),
    USER_NOT_PASSWORD(510,false,"当前账号还未设置密码，请使用验证码登录"),
    USER_NOT_NULL(511, false, "账号和密码不能为空"),
    USER_LOGIN_PWD_ERROR(512, false, "账号或密码错误，请重新输入"),
    USER_LOGIN_CODE_ERROR(513, false, "账号或验证码错误，请重新输入"),
    MAIL_CODE_NOT_EMPTY(514, false,"邮箱和验证码不能为空"),
    USER_NOT_EXIST(515, false, "不存在当前用户，请使用邮箱登录"),
    USER_UPLOAD_NULL_ERROR(516, false, "字段不能为空"),


    /* 邮箱相关 */
    MAIL_SEND_FAILED(554, false,"验证码发送失败，请重新发送"),
    MAIL_NEED_WAIT_ERROR(555,false,"验证码发送太快啦~请稍后再试！"),
    MAIL_CODE_EXPIRE(556,false,"邮箱验证码已过期，请重新发送"),
    MAIL_CODE_ERROR(557,false,"验证码不正确，请重新输入"),

    /* 文件相关 */
    FILE_FORMATTER_FAILD(572,false,"文件图片格式不支持！"),
    FILE_NOT_EXIST_ERROR(573,false,"你所查看的文件不存在！"),
    FILE_NAME_NOT(575,false,"文件名不能为空！"),
    FILE_UPLOAD_NULL_ERROR(570,false,"文件不能为空，请选择一个文件再上传！"),
    FILE_UPLOAD_ERROR(571,false,"文件上传失败，请重试！"),
    FILE_UPLOAD_COUNT_OVER(571,false,"您今天的上传次数已用完"),
    FILE_MAX_SIZE_ERROR(577,false,"仅支持2MB大小以下的图片上传！");


    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }
}
