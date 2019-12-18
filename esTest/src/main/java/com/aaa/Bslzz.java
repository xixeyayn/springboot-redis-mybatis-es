package com.aaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*  @  时间    :  2019/12/18 14:57:58
 *  @  类名    :  Bslzz
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@MapperScan("com.aaa.dao")
@SpringBootApplication
public class Bslzz {
    public static void main(String[] args) {
        SpringApplication.run(Bslzz.class,args);
    }
}
