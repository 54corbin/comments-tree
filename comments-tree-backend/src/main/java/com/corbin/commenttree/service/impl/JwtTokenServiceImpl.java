package com.corbin.commenttree.service.impl;

import com.alibaba.fastjson.JSON;
import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.exception.JwtExpiredException;
import com.corbin.commenttree.exception.JwtInvalidException;
import com.corbin.commenttree.service.JwtTokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


/**
 * @author corbin
 */
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    @Override
    public String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                type(JOSEObjectType.JWT)
                .build();
        //将负载信息封装到Payload中
        Payload payload = new Payload(payloadStr);
        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(secret);
        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    @Override
    public PayloadDto verifyTokenByHMAC(String token, String secret) throws ParseException, JOSEException, JwtInvalidException, JwtExpiredException {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        //创建HMAC验证器
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new JwtInvalidException("token签名不合法！");
        }
        String payload = jwsObject.getPayload().toString();
        PayloadDto payloadDto = JSON.parseObject(payload, PayloadDto.class);
        if (payloadDto.getExp() < System.currentTimeMillis()) {
            throw new JwtExpiredException("token已过期！");
        }
        return payloadDto;
    }

    @Override
    public PayloadDto getDefaultPayloadDto() {
        //过期时间
        LocalDateTime exp = LocalDateTime.now().plusMinutes(30);
        return PayloadDto.builder()
                .iat(System.currentTimeMillis())
                .exp(exp.toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .jti(UUID.randomUUID().toString())
                .username("admin")
                .userId(0L)
                .build();
    }
}

