package com.aaa.server;

import com.aaa.entity.Book;

import java.util.List;
import java.util.Map;

/*  @  时间    :  2019/12/18 15:00:21
 *  @  类名    :  BookService
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
public interface BookService {
    /**
     *  sql转es
     * @return
     */
    Map<String,Object> sqlToEs();

    /**
     *  查找全部
     * @return
     */
    List<Map<String, Object>> findAll();

    /**
     *  删除全部
     * @return
     */
    Map<String ,Object> deleAll();

    /**
     *  查找根据ID
     * @param id
     * @return
     */
    Map<String ,Object> selebyid(Integer id);

    /**
     *  删除根据ID
     * @param id
     * @return
     */
    Map<String ,Object> delebyid(Integer id);

    /**
     *  更改数据
     * @param book1
     * @return
     */
    Map<String ,Object> update(Book book1);
}
