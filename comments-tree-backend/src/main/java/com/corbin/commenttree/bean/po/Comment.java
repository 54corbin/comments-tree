package com.corbin.commenttree.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 与存储留言树的表对应(按左右值编码算法结构存储)
 * @author corbin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的用户表id
     */
    @TableField(value = "user_id")
    private Long userId;

    private String username;

    /**
     * 留言或注释内容
     */
    private String content;

    /**
     * 节点左编码
     */
    private Long lft;

    /**
     * 节点右编码
     */
    private Long rgt;

    /**
     * 父级id（融合邻接表方案，用户加速插入）
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 当前节点层级
     */
    private Integer level;

    /**
     * 创建时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "gmt_modified")
    private LocalDateTime gmtModified;

}
