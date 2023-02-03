package com.nowcoder.community.enums;

public enum HttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登陆后再操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503,"邮箱已存在"),
    REQUIRE_USERNAME(504,"用户名必须填写"),
    REQUIRE_PASSWORD(505,"密码必须填写"),
    ACTIVATION_REPEAT(506,"账号重复激活"),
    ACTIVATION_FAILURE(507,"激活失败"),
    NOT_EXIST(508,"数据不存在");

    int code;
    String msg;
    HttpCodeEnum(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
