package com.corbin.commenttree.service;

import com.corbin.commenttree.bean.po.Comment;
import com.corbin.commenttree.bean.vo.CommentVO;
import com.corbin.commenttree.bean.vo.PageRestResult;
import com.corbin.commenttree.bean.vo.RestResult;

/**
 * 定义用于操作留言树的接口
 * @author corbin
 */
public interface CommentTreeService {


    /**
     * 提交留言或回复（父级id为0时代表留言）
     * @param userId 用户id
     * @param content 留言或回复内容
     * @param parentId 父级id（所有留言的父级id固定为0）
     * @return 成功与否
     */
    RestResult<CommentVO> submit(Long userId, String content, Long parentId);

    /**
     * 分页获取指定节点下的留言或回复
     * @param parentId 要获取的留言或回复的父级id（父级id为0时代表留言）
     * @param deep 需要查询的深度（要向下查几层）
     * @param size 每页的大小
     * @param currentPage 当前页码
     * @return 留言或回复数据
     */
    PageRestResult<CommentVO> getComments(Long parentId, Integer deep, Integer size, Integer currentPage);
}
