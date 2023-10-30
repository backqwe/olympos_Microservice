package org.olympos.athena.cache.connection;

import org.olympos.athena.cache.config.RedisBaseProperties;
import org.olympos.athena.cache.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * ConnectionFactoryBuilder
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-07-26 23:39
 */
@Component
public class ConnectionFactoryBuilder {

    // 哨兵模式
    private final static String SENTINEL_MODEL = "1";
    // 集群模式
    private final static String CLUSTER_MODEL = "2";

    // 基础配置
    @Autowired
    private RedisBaseProperties redisBaseProperties;
    @Autowired
    private RedisConfig redisConfig;

    /**
     * redis连接类工厂方法
     *
     * Standalone,Sentinel,RedisCluster 三个环境配置其中之一(对应 独立redis，哨兵，集群 三个模式) + JedisClient 客户端配置(连接池相关)
     *
     * @return RedisConnectionFactory Redis连接工厂类
     */
    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {

        LettuceConnectionFactory lettuce;

        // Redis哨兵环境
        if ( SENTINEL_MODEL.equals( redisBaseProperties.getClusterMode()))
            lettuce = redisConfig.sentinelConnectionFactory();

            // Redis集群环境
        else if ( CLUSTER_MODEL.equals( redisBaseProperties.getClusterMode()))
            lettuce = redisConfig.clusterConnectionFactory();

            // Redis单服务环境
        else
            lettuce = redisConfig.standaloneConnectionFactory();

        return lettuce;
    }
}
