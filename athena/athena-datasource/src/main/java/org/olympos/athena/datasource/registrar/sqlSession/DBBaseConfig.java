package org.olympos.athena.datasource.registrar.sqlSession;

import lombok.Data;

/**
 * DBBaseConf
 *
 * @Describe:
 *   数据源的基本配置
 * @Author Chasen Hao
 * @Date 2023-04-11 01:35
 */
@Data
class DBBaseConfig {

    private String host;
    private int    port;
    private String db;
    private String username;
    private String password;
    private String mappersPath;
    private int    maxActive;
    private int    minIdle;
    private int    maxIdle;
    private int    validationInterval            = 30000;
    private int    validationQueryTimeout        = 5;
    private int    timeBetweenEvictionRunsMillis = 1800000;
    private int    initialSize;
    private int    maxWait                       = 10000;
    private int    removeAbandonedTimeout        = 60;
    private int    minEvictableIdleTimeMillis    = 30000;
}
