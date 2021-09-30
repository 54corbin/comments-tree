package com.corbin.commenttree.controller;

import com.corbin.commenttree.annotation.AuthRequired;
import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.bean.vo.CommentVO;
import com.corbin.commenttree.bean.vo.PageRestResult;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.service.CommentTreeService;
import com.corbin.commenttree.util.ThreadLocalUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * 用于操作留言树
 * @author corbin
 */
@Validated
@RestController
@RequestMapping(path = "/v1/comment")
public class CommentController {

    private final CommentTreeService commentTreeService;

    public CommentController(CommentTreeService commentTreeService) {
        this.commentTreeService = commentTreeService;
    }

    /**
     * 提交留言或回复（父级id为0时代表留言）
     * @param content 留言或回复内容
     * @param parentId 父级id（所有留言的父级id固定为0）
     * @return 成功与否
     */
    @AuthRequired
    @PostMapping
    public RestResult<CommentVO> submit(@Length(min = 3,max = 200,message = "请输入3~200字的内容") @RequestParam String content,
                                        @Min(value = 0) @RequestParam Long parentId) {

        PayloadDto jwtPayload = ThreadLocalUtil.get("jwtPayload");
        return commentTreeService.submit(jwtPayload.getUserId(), jwtPayload.getUsername(), content,parentId);
    }

    /**
     * 分页获取指定节点下的留言或回复
     * @param parentId 要获取的留言或回复的父级id（父级id为0时代表留言）
     * @param deep 需要查询的深度（要向下查几层）
     * @param size 每页的大小
     * @param currentPage 当前页码
     * @return 留言或回复数据
     */
    @GetMapping
    PageRestResult<CommentVO> getComments(@Min(value = 0) @RequestParam Long parentId,
                                          @Min(value = 0) @RequestParam Integer deep, @Min(value = 1) @RequestParam Integer size,@Min(value = 1) @RequestParam Integer currentPage){
        return commentTreeService.getComments(parentId,deep,size,currentPage);
    }

}
