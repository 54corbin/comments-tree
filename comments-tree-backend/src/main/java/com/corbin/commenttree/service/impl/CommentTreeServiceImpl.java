package com.corbin.commenttree.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.corbin.commenttree.bean.po.Comment;
import com.corbin.commenttree.bean.vo.CommentVO;
import com.corbin.commenttree.bean.vo.PageRestResult;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.dao.CommentMapper;
import com.corbin.commenttree.service.CommentTreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于操作留言树的接口实现
 * @author corbin
 */
@Service
public class CommentTreeServiceImpl implements CommentTreeService {

    //留言或回复最小长度
    private final int MIN_CONTENT_LENGTH =3;

    //留言或回复最大长度
    private final int MAX_CONTENT_LENGTH =300;

    private final CommentMapper commentMapper;

    public CommentTreeServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public RestResult<CommentVO> submit(Long userId, String content, Long parentId) {
        //参数检查
        if(null == userId || null == parentId || StringUtils.isEmpty(content)){
            return RestResult.fail("参数异常");
        }

//        内容应介于3~300字之间
        if(content.length() < MIN_CONTENT_LENGTH || content.length() > MAX_CONTENT_LENGTH){
            return RestResult.fail("内容应介于3~300字之间");
        }

        //获取父节点详情
        Comment pc = commentMapper.selectById(parentId);
        //更新右值大于父节点右值的节点
        commentMapper.increaseRgt(pc.getRgt(),2);
        //更新左值大于父节点右值的节点
        commentMapper.increaseLft(pc.getRgt(),2);

        //计算新节点的左右值
        Long subLft = pc.getRgt();
        Long subRgt = pc.getRgt() + 1;

        // 计算新加节点的层级
        Integer subLevel = new LambdaQueryChainWrapper<>(commentMapper)
                .le(Comment::getLft, subLft)
                .ge(Comment::getRgt, subRgt)
                .count();

        LocalDateTime now = LocalDateTime.now();
        //构造待插入的节点，并插入
        Comment sub = Comment.builder()
                .userId(userId)
                .level(subLevel)
                .content(content)
                .lft(subLft)
                .rgt(subRgt)
                .parentId(parentId)
                .gmtCreate(now)
                .gmtModified(now)
                .build();
        //insert into comment_tree(name,lft,rgt) values(p_node_name,p_rgt,p_rgt+1);
        commentMapper.insert(sub);

        //子节点总数 = (右值 – 左值– 1) / 2
        //构造响应VO
        int childrenCount = (int) (sub.getRgt() - sub.getLft() - 1) / 2;
        CommentVO respVO = CommentVO.builder()
                .id(sub.getId())
                .userId(sub.getUserId())
                .parentId(sub.getParentId())
                .content(sub.getContent())
                .createTime(sub.getGmtCreate())
                .childrenCount(childrenCount)
                .level(subLevel)
                .build();
        return RestResult.success(respVO);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageRestResult<CommentVO> getComments(Long parentId, Integer deep, Integer size, Integer currentPage) {

        //参数检查
        if( null == parentId || deep < 0 || size <= 0 || currentPage <= 0){
            RestResult<CommentVO> result = RestResult.fail("参数异常");
            return (PageRestResult)result;
        }

        //查得父节点详情
        Comment parent = commentMapper.selectById(parentId);

        //查询父节点下的子节点
        IPage<Comment> page = new LambdaQueryChainWrapper<>(commentMapper)
                .between(Comment::getLft, parent.getLft(), parent.getRgt())
                .le(Comment::getLevel, parent.getLevel() + deep)
                .orderByAsc(Comment::getLevel)
                .orderByDesc(Comment::getGmtCreate)
                .page(new Page<>(currentPage, size));

        //将查得的子节点列表转为与前端协商的vo对象
        List<CommentVO> voList = page.getRecords().stream().map(po -> {
            //子节点总数 = (右值 – 左值– 1) / 2
            //构造响应VO
            int childrenCount = (int) (po.getRgt() - po.getLft() - 1) / 2;

            //依据po构造vo
            return CommentVO.builder()
                    .id(po.getId())
                    .userId(po.getUserId())
                    .parentId(po.getParentId())
                    .content(po.getContent())
                    .createTime(po.getGmtCreate())
                    .childrenCount(childrenCount)
                    .level(po.getLevel())
                    .build();
        }).collect(Collectors.toList());

        return PageRestResult.success(page.getPages(),page.getTotal(),page.getSize(),page.getCurrent(),voList);

    }
}
