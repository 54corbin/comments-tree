package com.corbin.commenttree.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 对应用户表
 * @author corbin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("m_user")
public class MUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

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
