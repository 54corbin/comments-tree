package com.corbin.commenttree.service;

import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.exception.JwtExpiredException;
import com.corbin.commenttree.exception.JwtInvalidException;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/**
 * @author corbin
 */
public interface JwtTokenService {
    String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException;

    PayloadDto verifyTokenByHMAC(String token, String secret) throws ParseException, JOSEException, JwtInvalidException, JwtExpiredException;

    PayloadDto getDefaultPayloadDto();
}
