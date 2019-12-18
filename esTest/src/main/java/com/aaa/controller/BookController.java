package com.aaa.controller;

import com.aaa.entity.Book;
import com.aaa.server.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*  @  时间    :  2019/12/18 16:48:50
 *  @  类名    :  BookController
 *  @  创建人  :  Xie
 *  @  描述    :
 *  es redis 结合测试
 */
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
@RequestMapping("/findAll")
    public List< Map<String,Object>> findAll(){
        List<Map<String, Object>> all = bookService.findAll();
        return all;
    }
    @RequestMapping("/deleAll")
    public  Map<String,Object> deleAll(){
        Map<String, Object> map = bookService.deleAll();
        return map;
    }
    @RequestMapping("/test")
    public  Map<String,Object> test(){
        Map<String, Object> map = bookService.sqlToEs();
        return map;
    }
    @RequestMapping("/findbyid")
    public  Map<String,Object> findbyid(){
        Map<String, Object> map = bookService.selebyid(1);
        return map;
    }
    @RequestMapping("/delebyid")
    public  Map<String,Object> delebyid(){
        Map<String, Object> map = bookService.delebyid(1);
        return map;
    }
    @RequestMapping("/update")
    public  Map<String,Object> update(){
        Book book = new Book();
        book.setBookPrice(23);
        book.setBookName("我弟是是是");
        book.setId(1);
        Map<String, Object> map = bookService.update(book);
        return map;
    }
}
