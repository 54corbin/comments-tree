package com.corbin.commenttree.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.corbin.commenttree.bean.po.MUser;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问对象
 * @author corbin
 */
@Repository
public interface UserMapper extends BaseMapper<MUser> {
}