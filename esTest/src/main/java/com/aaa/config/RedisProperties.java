package com.aaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*  @  时间    :  2019/12/18 16:01:20
 *  @  类名    :  RedisProperties
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private String nodes;
    private String maxattempts;
    private String maxtimeout;
    private String expire;

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public void setMaxattempts(String maxattempts) {
        this.maxattempts = maxattempts;
    }

    public void setMaxtimeout(String maxtimeout) {
        this.maxtimeout = maxtimeout;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getNodes() {
        return nodes;
    }

    public String getMaxattempts() {
        return maxattempts;
    }

    public String getMaxtimeout() {
        return maxtimeout;
    }

    public String getExpire() {
        return expire;
    }
}
