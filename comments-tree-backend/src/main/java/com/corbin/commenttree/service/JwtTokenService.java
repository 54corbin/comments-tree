package com.corbin.commenttree.service;

import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.exception.JwtExpiredException;
import com.corbin.commenttree.exception.JwtInvalidException;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/**
 * 生成和校验jwt token
 * @author corbin
 */
public interface JwtTokenService {
    /**
     * 生成jwt token
     * @param payloadStr token 里携带的内容
     * @param secret 生成jwt token 所需的的密钥
     * @return token
     * @throws JOSEException 无法加密
     */
    String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException;

    /**
     * 校验jwt token
     * @param token token 字符串
     * @param secret 生成jwt token 所需的的密钥
     * @return token中携带的数据
     * @throws ParseException 无法解析token
     * @throws JOSEException 无法解密
     * @throws JwtInvalidException 非法token
     * @throws JwtExpiredException token已过期
     */
    PayloadDto verifyTokenByHMAC(String token, String secret) throws ParseException, JOSEException, JwtInvalidException, JwtExpiredException;

    /**
     * 快速生成默认jwt载荷
     * @return 默认jwt载荷
     */
    PayloadDto getDefaultPayloadDto();
}
