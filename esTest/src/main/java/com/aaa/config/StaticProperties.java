package com.aaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*  @  时间    :  2019/12/18 15:23:08
 *  @  类名    :  StaticProperties
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@PropertySource("classpath:config/staticvalue.properties")
@Component
@ConfigurationProperties(prefix = "static")
public class StaticProperties {

    private String clusterName;
    private String nodeName;
    private String threadSearchSize;
    private String transportSniff;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getThreadSearchSize() {
        return threadSearchSize;
    }

    public void setThreadSearchSize(String threadSearchSize) {
        this.threadSearchSize = threadSearchSize;
    }

    public String getTransportSniff() {
        return transportSniff;
    }

    public void setTransportSniff(String transportSniff) {
        this.transportSniff = transportSniff;
    }
}

