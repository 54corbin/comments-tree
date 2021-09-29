package com.corbin.commenttree.service;

import com.corbin.commenttree.bean.dto.UserDto;
import com.corbin.commenttree.bean.vo.RestResult;
import org.springframework.validation.annotation.Validated;

/**
 * 处理用户相关业务
 * @author corbin
 */
public interface UserService {

    /**
     * 登录
     * @param user 登录验证信息
     * @return token
     */
    RestResult<String> login(UserDto user);

    /**
     * 注册
     * @param user 注册信息
     * @return 新用户id
     */
    RestResult<Long> register(@Validated UserDto user);
}
