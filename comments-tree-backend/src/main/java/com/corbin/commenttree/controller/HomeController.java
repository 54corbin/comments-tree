package com.corbin.commenttree.controller;

import com.corbin.commenttree.bean.vo.RestResult;
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
