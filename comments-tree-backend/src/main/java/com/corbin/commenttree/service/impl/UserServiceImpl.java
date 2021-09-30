package com.corbin.commenttree.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.bean.dto.UserDto;
import com.corbin.commenttree.bean.po.MUser;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.dao.UserMapper;
import com.corbin.commenttree.service.JwtTokenService;
import com.corbin.commenttree.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * @author corbin
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final JwtTokenService jwtTokenService;

    private final UserMapper userMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.max-duration}")
    private long maxDuration;

    public UserServiceImpl(JwtTokenService jwtTokenService, UserMapper userMapper) {
        this.jwtTokenService = jwtTokenService;
        this.userMapper = userMapper;
    }

    @SneakyThrows
    @Override
    public RestResult<String> login(UserDto user) {

        if (null == user || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getUsername())){
            return RestResult.fail("参数不合法");
        }

        //获取用户信息
        MUser m = new LambdaQueryChainWrapper<>(userMapper)
                .eq(MUser::getUsername, user.getUsername())
                .or()
                .eq(MUser::getEmail, user.getUsername())
                .eq(MUser::getPassword, DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))
                .one();

        //生成token并返回
        return Optional.ofNullable(m)
                .map(u->{
                    PayloadDto payload = jwtTokenService.getDefaultPayloadDto();
                    payload.setUserId(u.getId());
                    payload.setUsername(u.getUsername());

                    //勾选了记住我
                    if(user.isRemember()){
                        LocalDateTime exp = LocalDateTime.now().plusSeconds(maxDuration);
                        payload.setExp(exp.toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    }

                    try {
                        String token = jwtTokenService.generateTokenByHMAC(JSON.toJSONString(payload), DigestUtils.md5DigestAsHex(secret.getBytes()));
                        return RestResult.success(token);
                    } catch (JOSEException e) {
                        log.error("JOSEException",e);
                        return RestResult.<String>error();
                    }
                }).orElse(RestResult.fail("用户不存在或密码错误"));

    }

    @Override
    public RestResult<Long> register(UserDto user) {

        //验证用户名或邮箱地址是否已存在
        Integer exist = new LambdaQueryChainWrapper<>(userMapper)
                .eq(MUser::getUsername, user.getUsername())
                .or()
                .eq(MUser::getEmail, user.getEmail())
                .count();
        if (exist > 0){
            return RestResult.fail("用户名或邮箱地址已存在");
        }

//        生成user的po对象准备入库
        String digestPsw = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        MUser mUser = MUser.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(digestPsw)
                .gmtCreate(LocalDateTime.now())
                .gmtModified(LocalDateTime.now())
                .build();
        //入库
        int insert = userMapper.insert(mUser);

        if(1 != insert){
            return RestResult.error();
        }

        return RestResult.success(mUser.getId());
    }

}
