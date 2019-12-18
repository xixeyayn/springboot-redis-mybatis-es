package com.aaa.server.impl;

import com.aaa.dao.BookDao;
import com.aaa.entity.Book;
import com.aaa.server.BookService;
import com.aaa.until.EsService;
import com.aaa.until.EsSql;
import com.aaa.until.RedisService;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.aaa.until.StatusEnum.OPRATION_SUCCESS;
import static com.aaa.until.UserStatic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*  @  时间    :  2019/12/18 15:00:05
 *  @  类名    :  BookServiceImpl
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private EsService esService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private RedisService redisService;
    public static Map<String,Object> map = new HashMap<String, Object>();
    @Override
    public Map<String, Object> sqlToEs() {
        map.clear();
        List<Book> all = bookDao.findAll();

        for (Book b : all) {
             map = EsSql.objectMap(b);
            EsService.addData(map,TABLE,BOOK,String.valueOf(b.getId()));
        }
        map.clear();
        map.put(CODE,OPRATION_SUCCESS.getCode());
        map.put(MSG,OPRATION_SUCCESS.getMsg());
        return map;
    }

    @Override
    public List<Map<String, Object>> findAll() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        map.clear();
        String book = redisService.get(BOOKID);
        if(StringUtils.isNullOrEmpty(book)){
             list = EsService.seleAllDate(TABLE, BOOK, null, null, null, null, null);

            redisService.set(BOOKID, JSON.toJSONString(list));

        }else {
            List<Book> books = JSON.parseArray(book, Book.class);
            for (Book b : books) {
                Map<String, Object> maps = EsSql.objectMap(b);
               list.add(maps);
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> deleAll() {
        map.clear();
        Long del = redisService.del(BOOKID);
        Map<String, Object> map = EsService.deleteIndex(TABLE);
        return map;
    }

    @Override
    public Map<String, Object> selebyid(Integer id) {
        map.clear();
        String s = redisService.get(BOOKID+String.valueOf(id));
        if(StringUtils.isNullOrEmpty(s)){
            map = EsService.seleDateByid(TABLE, BOOK, String.valueOf(id),null );
            if(null==map){
                return null;
            }
            Book  book2 =JSON.parseObject(JSON.toJSONString(map), Book.class);
            String id1 = book2.getBookName();
            redisService.set(BOOKID+id1,JSON.toJSONString(map));

        }else {
            map.put("msg", JSON.parseArray(s,Book.class));
            map.put("code",200);
        }
        return map;
    }

    @Override
    public Map<String, Object> delebyid(Integer id) {
        map.clear();
        Long del = redisService.del(BOOKID+String.valueOf(id));
        Map<String, Object> map = EsService.deleteDataById(TABLE, BOOK, String.valueOf(id));
        return map;
    }

    @Override
    public Map<String, Object> update(Book book) {
        map.clear();
        map = EsSql.objectMap(book);
        map = EsService.updateData(map, TABLE, BOOK, String.valueOf(book.getId()));
        Long del = redisService.del(BOOKID+String.valueOf(book.getId()));
        return map;
    }
}
