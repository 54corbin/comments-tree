package com.corbin.commenttree.service.impl;

import com.corbin.commenttree.bean.dto.UserDto;
import com.corbin.commenttree.bean.po.MUser;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.dao.UserMapper;
import com.corbin.commenttree.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

@Order(1)
    @Test
    void register() {
        UserDto userDto = new UserDto("firstUser","corbin@gmail.com","1q2w$rr1w",false);

        RestResult<Long> resp = userService.register(userDto);
        assert resp.getSuccess();
        MUser newUser = userMapper.selectById(resp.getData());
        assert null != newUser;
        assert userDto.getUsername().equals(newUser.getUsername());
        assert userDto.getEmail().equals(newUser.getEmail());
        assert !userDto.getPassword().equals(newUser.getPassword());

        /*
         *重复邮箱
         */
        userDto.setUsername(null);
        RestResult<Long> resp2 = userService.register(userDto);
        assert !resp2.getSuccess();

        /*
         *重复用户名
         */
        userDto.setUsername("firstUser");
        RestResult<Long> resp3 = userService.register(userDto);
        assert !resp3.getSuccess();


    }

    @Order(2)
    @Test
    void login() {
        UserDto userDto = new UserDto();

        /*
        *都为空
         */
        RestResult<String> resp = userService.login(userDto);
        assert !resp.getSuccess();

        /*
         *只有用户名
         */
        userDto.setUsername("firstUser");
        RestResult<String> resp2 = userService.login(userDto);
        assert !resp2.getSuccess();

        /*
         *只有邮箱
         */
        userDto.setUsername(null);
        userDto.setEmail("corbin@gmail.com");
        RestResult<String> resp3 = userService.login(userDto);
        assert !resp3.getSuccess();

        /*
         *只有密码
         */
        userDto.setEmail(null);
        userDto.setPassword("1q2w$rr1w");
        RestResult<String> resp4 = userService.login(userDto);
        assert !resp4.getSuccess();

        /*
         *用户名+密码
         */
        userDto.setUsername("firstUser");
        userDto.setEmail(null);
        userDto.setPassword("1q2w$rr1w");
        RestResult<String> resp5 = userService.login(userDto);
        assert resp5.getSuccess();
        assertNotNull(resp5.getData());

        /*
         *邮箱+密码
         */
        userDto.setUsername(null);
        userDto.setEmail("corbin@gmail.com");
        userDto.setPassword("1q2w$rr1w");
        RestResult<String> resp6 = userService.login(userDto);
        assert resp6.getSuccess();
        assertNotNull(resp6.getData());

    }
}