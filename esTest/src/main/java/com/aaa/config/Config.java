package com.aaa.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.aaa.config.StaticProperties;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/*  @  时间    :  2019/12/18 15:04:09
 *  @  类名    :  EsConfig
 *  @  创建人  :  Xie
 *  @  描述    :
 * 配置类
 *
 */
@SpringBootApplication
public class Config {
    @Autowired
    private EsProperties esProperties;
    @Autowired
    private StaticProperties staticProperties;
    @Autowired
    private RedisProperties redisProperties;
    //配置es
@Bean
    public TransportClient getTransportClient(){
        TransportClient client =null;
        try {
            Settings settings = Settings.builder()
                    .put(staticProperties.getClusterName(),esProperties.getClusterName())
                    .put(staticProperties.getNodeName(),esProperties.getNodeName())
                    .put(staticProperties.getTransportSniff(), true)
                    .put(staticProperties.getThreadSearchSize(), esProperties.getPool()).build();
            client = new PreBuiltTransportClient(settings);
            TransportAddress transportAddress = new TransportAddress
                    (InetAddress.getByName(esProperties.getIp()),Integer.parseInt(esProperties.getPort()));
            client.addTransportAddress(transportAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
return client;
    }
//配置redis
    @Bean
    public JedisCluster getJedisCluster(){
        String nodes = redisProperties.getNodes();
        String[] nodeArrey = nodes.split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for(String node:nodeArrey){
            String[] split = node.split(":");
            HostAndPort hostAndPort = new HostAndPort(split[0], Integer.parseInt(split[1]));
            hostAndPorts.add(hostAndPort);
        }
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts, Integer.parseInt(redisProperties.getMaxtimeout()), Integer.parseInt(redisProperties.getMaxattempts()));
        return jedisCluster;
    }
}
