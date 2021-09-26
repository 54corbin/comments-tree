package com.corbin.commentstree.controller;

import com.corbin.commentstree.bean.vo.RestResult;
import com.corbin.commentstree.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 主页rest接口
 * @author corbin
 */
@Validated
@RestController
@RequestMapping(path = "/v1/")
public class HomeController {

    @GetMapping("hello")
    public RestResult<Object> hello( @RequestParam @NotEmpty String msg){
        return RestResult.builder()
                .success(true)
                .message("success")
                .status(200)
                .data("hello你好 "+msg)
                .build();
    }
}
