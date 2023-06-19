package org.olympos.athena.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存属性配置类
 *
 * @Describe:
 *
 * redis:
 *   base:
 *     masterHost:
 *     masterPort:
 *     clusterMode:
 *     password:
 *     sentinelPassword:
 *     timeout: 300
 *     pool-max-active: 500
 *     pool-max-wait: -1
 *     pool-max-idle: 150
 *     pool-min-idle: 100
 *     pool-test-onBorrow: true
 *   slave-servers:
 *     - host: 192.168.1.13
 *       port: 6379
 *
 *     - host: 192.168.1.13
 *       port: 6379
 *
 * @Author Chanse Hao
 * @Date 2021-02-23 06:48
 */
@ConfigurationProperties(prefix = "redis.slave-servers")
public class RedisServerProperties {

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
