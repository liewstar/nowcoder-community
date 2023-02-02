package com.nowcoder.community.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nowcoder.community.enums.HttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> implements Serializable {
    private int status;
    private String message;
    private T data;

    public JsonResult() {
        this.status=HttpCodeEnum.SUCCESS.getCode();
        this.message=HttpCodeEnum.SUCCESS.getMsg();
    }
    public JsonResult(int status,T data) {
        this.status=status;
        this.data=data;
    }
    public JsonResult(int status,String message,T data) {
        this.status=status;
        this.data=data;
        this.message=message;
    }
    public JsonResult(int status,String message) {
        this.status=status;
        this.message=message;
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> jsonResult=new JsonResult<>();
        jsonResult.setStatus(HttpCodeEnum.SUCCESS.getCode());
        jsonResult.setMessage(HttpCodeEnum.SUCCESS.getMsg());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult<T> fail(int status,String message) {
        JsonResult<T> jsonResult=new JsonResult<>();
        jsonResult.setStatus(status);
        jsonResult.setMessage(message);
        return jsonResult;
    }
}
