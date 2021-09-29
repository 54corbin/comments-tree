package com.corbin.commenttree.bean.vo;

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
     * 通用返回成功
     * @return 统一响应对象
     */
    public static RestResult<Object> success(){
        return RestResult.builder()
                .success(true)
                .status(RestCodeEnum.SUCCESS.code)
                .message(RestCodeEnum.SUCCESS.message)
                .build();
    }

    /**
     * 通用返回成功(带数据体)
     * @return 统一响应对象
     */
    public static<T> RestResult<T> success(T data){
        return new RestResult<>(true, RestCodeEnum.SUCCESS.code, RestCodeEnum.SUCCESS.message, data);

    }

    /**
     * 通用返回失败（带失败说明消息）
     * @param msg 失败说明消息
     * @return 统一响应对象
     */
    public static<T> RestResult<T> fail(String msg){
        return new RestResult<>(false,RestCodeEnum.FAIL.code,msg,null);
    }

    /**
     * 通用返回失败（带失败说明消息和状态码）
     * @param msg 失败说明消息
     * @param status 状态码
     * @return 统一响应对象
     */
    public static RestResult<Object> fail(String msg,Integer status){
        return new RestResult<>(false,status,msg,null);
    }

    /**
     * 通用返回服务器异常
     * @return 统一响应对象
     */
    public static<T> RestResult<T> error(){
        return new RestResult<>(false,RestCodeEnum.ERROR.code,RestCodeEnum.ERROR.message,null);
    }
}
