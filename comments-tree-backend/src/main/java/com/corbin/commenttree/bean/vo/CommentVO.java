package com.corbin.commenttree.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 代表留言或回复对象
 * @author corbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {

    /**
     * 留言或回复对象id
     */
    private Long id;

    /**
     * 发表该留言或回复的用户id
     */
    private Long userId;

    /**
     * 留言或回复数据
     */
    private String content;

    /**
     * 该留言或回复对象所在层级
     */
    private Integer level;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 该节点下拥有的子节点数量
     */
    private Integer childrenCount;

    /**
     * 该留言或回复对象的提交时间
     */
    private LocalDateTime createTime;
}
