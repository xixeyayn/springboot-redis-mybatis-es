package com.aaa.until;

import com.aaa.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*  @  时间    :  2019/12/18 16:06:12
 *  @  类名    :  RedisService
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Service
public class RedisService {
    @Autowired
    private Config config;
    public String get(String key){
        return config.getJedisCluster().get(key);
    }
    public Long del(String key){
        return config.getJedisCluster().del(key);
    }
    public String set(String key,String value){
        return config.getJedisCluster().set(key,value);
    }
    public Long expire(String key,Integer seconds){
        return config.getJedisCluster().expire(key,seconds);
    }
}
