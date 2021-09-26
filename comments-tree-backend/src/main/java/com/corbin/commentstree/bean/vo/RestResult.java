package com.corbin.commentstree.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author corbin
 * 统一返回对象
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResult<T> {

    /**
     * 是否响应成功
     */
    private Boolean success;

    /**
     * 否响状态码
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据体
     */
    private T data;

    /**
     * 通用返回失败（带失败说明消息）
     * @param msg 失败说明消息
     * @return 统一响应对象
     */
    public static RestResult<Object> fail(String msg){
        return RestResult.builder()
                .success(false)
                .status(RestCodeEnum.FAIL.code)
                .message(msg)
                .build();
    }

    /**
     * 通用返回服务器异常
     * @return 统一响应对象
     */
    public static RestResult<Object> error(){
        return RestResult.builder()
                .success(false)
                .status(RestCodeEnum.ERROR.code)
                .message(RestCodeEnum.ERROR.message)
                .build();
    }
}
