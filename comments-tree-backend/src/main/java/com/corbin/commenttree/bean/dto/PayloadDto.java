package com.corbin.commenttree.bean.dto;

import lombok.*;

/**
 *
 * 用于封装JWT中存储的信息
 * @author corbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayloadDto {

    /**
     * 签发时间
     */
    private Long iat;
    /**
     * 过期时间
     */
    private Long exp;

    /**
     * WT的ID
     */
    private String jti;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户id
     */
    private Long userId;

}

