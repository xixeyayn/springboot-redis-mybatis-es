package com.aaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*  @  时间    :  2019/12/18 15:01:24
 *  @  类名    :  Esproperties
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Component
@ConfigurationProperties(prefix = "spring.es")
public class EsProperties {
    private String ip;
    private String port;
    private String clusterName;
    private String nodeName;
    private String pool;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

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

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
}
