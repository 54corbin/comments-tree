package com.corbin.commenttree.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.corbin.commenttree.bean.po.Comment;
import com.corbin.commenttree.bean.vo.CommentVO;
import com.corbin.commenttree.bean.vo.PageRestResult;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.dao.CommentMapper;
import com.corbin.commenttree.service.CommentTreeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CommentTreeServiceImplTest {

    @Autowired
    private CommentTreeService commentTreeService;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 测试提交一个留言
     */
    @Order(1)
    @Test
    void submitComment() {

        Integer beforeCount = new LambdaQueryChainWrapper<>(commentMapper).count();
        log.info("添加前共：{} 个节点",beforeCount);

        RestResult<CommentVO> response = commentTreeService.submit(0L, "sdlf", 0L);

        Integer afterCount = new LambdaQueryChainWrapper<>(commentMapper).count();
        log.info("添加结果:{}\n添加后共：{} 个节点",response,afterCount);
        assert afterCount == beforeCount + 1;
        log.info("测试提交一条留言 -------OK");

//        List<Comment> list = new LambdaQueryChainWrapper<Comment>(commentMapper).list();

    }

    /**
     * 测试获取指定父节点下的子节点
     */
    @Order(2)
    @Test
    void getComments() {
        PageRestResult<CommentVO> comments = commentTreeService.getComments(0L, 1, 10, 1);
    }

    /**
     * 测试节点插入、查询性能
     */
    @Order(3)
    @Test
    void testInsertPerformance(){

        long start = System.currentTimeMillis();

        /*
          创建100层
         */
        for (int i = 0; i <100; i++) {
            commentTreeService.submit(0L,"test", (long)i);
        }

        /*
         * 分别向每一层添加兄弟节点
         */
        for (int i = 0; i < 100; i++) {
            log.info("createTree..level..{}/100..",i);
            for (int j = 0; j < 100-i; j++) {
                commentTreeService.submit(0L,"test", (long)i);
            }
        }
        long end = System.currentTimeMillis();

        long duration = end - start;
//        List<Comment> list = new LambdaQueryChainWrapper<Comment>(commentMapper).list();

        Integer afterCount = new LambdaQueryChainWrapper<>(commentMapper).count();
        log.info("添加后共：{} 个节点,平均耗时：{} ms,总耗时：{} ms",afterCount,duration/afterCount,duration);

        //保证平均插入时长小于0.5秒
        assert duration/afterCount < 500;

        log.info("测试节点插入性能....ok");

//        PageRestResult<CommentVO> comments = commentTreeService.getComments(60L, 1, 10, 1);

    }

    /**
     * 测试获取子节点的性能
     */
    @Order(4)
    @Test
    void testGetPerformance() {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            log.info("获取节点 {}下的子节点.....{}% ", i, i);
            commentTreeService.getComments((long) i, 1, 100, 1);

        }
        long end = System.currentTimeMillis();

        long duration = end - start;
        log.info("平均耗时：{} ms,总耗时：{} ms", duration / 100, duration);

        //保证平均查询时长小于0.5秒
        assert duration / 100 < 500;

        log.info("测试获取子节点的性能....ok");

    }

}