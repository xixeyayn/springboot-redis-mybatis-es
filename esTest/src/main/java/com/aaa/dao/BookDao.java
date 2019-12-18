package com.aaa.dao;

import com.aaa.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*  @  时间    :  2019/12/18 14:59:04
 *  @  类名    :  BookDao
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Mapper
public interface BookDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> findAll();
}
