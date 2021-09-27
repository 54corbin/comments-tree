package com.corbin.commenttree.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 带分页的统一响应对象
 * @author corbin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRestResult<T> extends RestResult<List<T>> {

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总数据量
     */
    private Long total;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 当前页码
     */
    private Long pageIndex;


    /**
     * 通用返回成功(带数据体)
     * @return 统一响应对象
     */
    public static<T> PageRestResult<T> success(Long totalPage,Long total,Long pageSize,Long pageIndex,List<T> data){
        PageRestResult<T> result = new PageRestResult<>(totalPage, total, pageSize, pageIndex);
        result.setData(data);
        result.setSuccess(true);
        result.setStatus(RestCodeEnum.SUCCESS.code);
        result.setMessage(RestCodeEnum.SUCCESS.message);

        return result;
    }

}
