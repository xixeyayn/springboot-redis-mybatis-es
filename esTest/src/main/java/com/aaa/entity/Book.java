package com.aaa.entity;

import lombok.Data;

/*  @  时间    :  2019/12/18 14:49:16
 *  @  类名    :  Book
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Data
public class Book {
    private Integer id;

    private String bookName;

    private Integer bookPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Integer bookPrice) {
        this.bookPrice = bookPrice;
    }
}
