package com.corbin.commenttree.dao;

import com.corbin.commenttree.bean.po.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 测试增加大于等于指定右值的节点右值
     */
    @Test
    void increaseRgt() {
        int step = 2;
        Comment before = commentMapper.selectById(0L);
        log.info(" 更新前{}",before);
        int n = commentMapper.increaseRgt(2L,step);
        log.info("increaseRgt 更新{}条记录",n);
        Comment after = commentMapper.selectById(0L);
        log.info(" 更新后{}",after);

        assert after.getRgt() == before.getRgt() + step;
        log.info("test increaseRgt ----- OK");

    }

    /**
     * 测试增加大于等于指定左值的节点右值
     */
    @Test
    void increaseLft() {
        int step = 2;
        Comment before = commentMapper.selectById(0L);
        log.info(" 更新前{}",before);
        int n = commentMapper.increaseLft(1L,step);
        log.info("increaseLft 更新{}条记录",n);
        Comment after = commentMapper.selectById(0L);
        log.info(" 更新后{}",after);

        assert after.getLft() == before.getLft() + step;
        log.info("test increaseRgt ----- OK");
    }
}