package com.corbin.commenttree.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.corbin.commenttree.bean.po.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 留言树数据访问对象
 * @author corbin
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 增加大于等于指定右值的节点右值
     * @param start 起始点
     * @param step 步长（增加多少）
     * @return 影响的记录数量
     */
    @Update("update comment set rgt=rgt+#{step} where rgt>=#{start};")
    int increaseRgt(@Param("start") Long start, @Param("step") Integer step);

    /**
     * 增加大于等于指定左值的节点右值
     * @param start 起始点
     * @param step 步长（增加多少）
     * @return 影响的记录数量
     */
    @Update("update comment set lft=lft+#{step} where lft>=#{start};")
    int increaseLft(@Param("start") Long start, @Param("step") Integer step);
}