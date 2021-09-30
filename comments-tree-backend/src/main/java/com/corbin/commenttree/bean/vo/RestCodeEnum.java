package com.corbin.commenttree.bean.vo;

/**
 * @author corbin
 * rest接口状态码
 */

public enum RestCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200,"成功"),

    /**
     * 失败
     */
    FAIL(400,"失败"),

    /**
     * token已失效
     */
    TOKEN_EXPIRED(401,"无效token"),

    /**
     * 服务器出错
     */
    ERROR(500,"服务器出错"),

    ;

    /**
     * 状态码
     */
    public Integer code;

    /**
     * 错误消息
     */
    public String message;

    RestCodeEnum(Integer code,String msg) {
        this.code = code;
        this.message = msg;
    }
}
