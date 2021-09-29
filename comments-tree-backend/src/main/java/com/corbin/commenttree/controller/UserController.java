package com.corbin.commenttree.controller;

import com.corbin.commenttree.annotation.AuthRequired;
import com.corbin.commenttree.bean.dto.UserDto;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.service.JwtTokenService;
import com.corbin.commenttree.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 主页rest接口
 * @author corbin
 */
@RestController
@RequestMapping(path = "/v1/user")
public class UserController {

    private final JwtTokenService jwtTokenService;

    private final UserService userService;


    public UserController(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    /**
     * 登录获取token
     * @return token
     */
    @PostMapping("/token")
    public RestResult<String> login(@RequestBody UserDto user) {
        return userService.login(user);
    }

    /**
     * 注册
     * @return 新用户id
     */
    @PostMapping
    public RestResult<Long>  register(@Valid @RequestBody UserDto user) {
        return userService.register(user);
    }
}
