package com.corbin.commenttree.service.impl;

import com.alibaba.fastjson.JSON;
import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.exception.JwtExpiredException;
import com.corbin.commenttree.exception.JwtInvalidException;
import com.corbin.commenttree.service.JwtTokenService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class JwtTokenServiceImplTest {

    @Value("${jwt.header-name}")
    private String headerKey;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    void generateTokenByHMAC() throws JOSEException, JwtExpiredException, JwtInvalidException, ParseException {

        PayloadDto pl = jwtTokenService.getDefaultPayloadDto();

        //生成token
        String token = jwtTokenService.generateTokenByHMAC(JSON.toJSONString(pl), DigestUtils.md5DigestAsHex(secret.getBytes()));
        assertNotNull(token);

        //验证token
        PayloadDto pl2 = jwtTokenService.verifyTokenByHMAC(token, DigestUtils.md5DigestAsHex(secret.getBytes()));
        assertNotNull(pl2);
        assertEquals(pl.getExp(),pl2.getExp());
        assertEquals(pl.getIat(),pl2.getIat());
        assertEquals(pl.getJti(),pl2.getJti());
        assertEquals(pl.getUserId(),pl2.getUserId());
        assertEquals(pl.getUsername(),pl2.getUsername());

    }

}