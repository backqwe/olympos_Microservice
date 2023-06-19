package org.olympos.athena.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存属性配置类
 *
 * @Describe:
 *
 * redis:
 *   base:
 *     # master
 *     masterHost:
 *     masterPort:
 *     password:
 *
 *     # base
 *     # 1 = sentinelMode, 2 = clusterMode, otherNum = standaloneMode
 *     clusterMode:
 *     sentinelPassword:
 *     timeout: 300
 *     # clusterMode & sentinelMode
 *     refreshPeriod: 6
 *
 *     # connectionPool
 *     poolMaxTotal: 500
 *     poolMaxWait: -1
 *     poolMaxIdle: 150
 *     poolMinIdle: 100
 *     poolTestOnBorrow: true
 *   slave-servers:
 *      # - host: 192.168.1.13
 *      #   port: 6379
 *     - host: 192.168.1.13
 *       port: 6379
 *
 *     - host: 192.168.1.13
 *       port: 6379
 *
 * @Author Chanse Hao
 * @Date 2021-02-23 06:48
 */
@ConfigurationProperties(prefix = "redis.base")
public class RedisBaseProperties {

    /** 主服务器配置 */
    private String masterHost;
    private int masterPort;
    private String password;

    /** 基础配置 */
    // 集群模式
    // 1 = sentinelMode(哨兵), 2 = clusterMode(集群), 其它 = standaloneMode(单服务)
    private String clusterMode = "0";
    private String sentinelPassword = "";
    private int timeout = 300;
    // 群集拓扑刷新时间
    private int refreshPeriod = 6;

    /** 连接池配置 */
    private int poolMaxTotal = 500;
    private int poolMaxWait = -1;
    private int poolMaxIdle = 150;
    private int poolMinIdle = 100;
    // jedis实例验证
    // borrow jedis实例时，进行validate操作；结果true，则jedis实例可用
    private boolean poolTestOnBorrow = true;

    public String getMasterHost() {
        return masterHost;
    }

    public void setMasterHost(String masterHost) {
        this.masterHost = masterHost;
    }

    public int getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(int masterPort) {
        this.masterPort = masterPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClusterMode() {
        return clusterMode;
    }

    public void setClusterMode(String clusterMode) {
        this.clusterMode = clusterMode;
    }

    public String getSentinelPassword() {
        return sentinelPassword;
    }

    public void setSentinelPassword(String sentinelPassword) {
        this.sentinelPassword = sentinelPassword;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(int refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(int poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    public boolean isPoolTestOnBorrow() {
        return poolTestOnBorrow;
    }

    public void setPoolTestOnBorrow(boolean poolTestOnBorrow) {
        this.poolTestOnBorrow = poolTestOnBorrow;
    }
}
