package org.olympos.athena.datasource.registrar.sqlSession;

import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * DBSupporter
 *
 * @Describe:
 *   数据源的配置
 * @Author Chasen Hao
 * @Date 2023-04-11 01:36
 */
class DBSupporter {

    private static String url                = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull";
    private static String driver             = "com.mysql.cj.jdbc.Driver";
    public static int     DEFAULT_MAX_IDLE   = 10;
    public static int     DEFAULT_MIN_IDLE   = 2;
    public static int     DEFAULT_MAX_ACTIVE = 20;

    public static PoolProperties buildPoolProperties(DBBaseConfig dbProperties) {

        if ( dbProperties == null)
            return null;
        if ( dbProperties.getMaxIdle() <= 0)
            dbProperties.setMaxIdle( DEFAULT_MAX_IDLE);
        if ( dbProperties.getMaxActive() <= 0)
            dbProperties.setMaxActive( DEFAULT_MAX_ACTIVE);
        if ( dbProperties.getMinIdle() <= 0)
            dbProperties.setMinIdle( DEFAULT_MIN_IDLE);
        if ( dbProperties.getInitialSize() <= 0)
            dbProperties.setInitialSize( dbProperties.getMinIdle());

        PoolProperties p = new PoolProperties();
        p.setDriverClassName( driver);
        p.setUrl( String.format( url, dbProperties.getHost(), dbProperties.getPort(), dbProperties.getDb()));
        p.setUsername( dbProperties.getUsername());
        p.setPassword( dbProperties.getPassword());
        p.setTestWhileIdle( true);
        p.setTestOnBorrow( true);
        p.setValidationQuery( "SELECT 1");
        p.setValidationInterval( dbProperties.getValidationInterval());
        p.setValidationQueryTimeout( dbProperties.getValidationQueryTimeout());
        p.setTimeBetweenEvictionRunsMillis( dbProperties.getTimeBetweenEvictionRunsMillis());
        p.setMaxActive( dbProperties.getMaxActive());
        p.setInitialSize( dbProperties.getInitialSize());
        p.setMaxWait( dbProperties.getMaxWait());
        p.setRemoveAbandonedTimeout( dbProperties.getRemoveAbandonedTimeout());
        p.setMinEvictableIdleTimeMillis( dbProperties.getMinEvictableIdleTimeMillis());
        p.setInitSQL( "set names utf8mb4");
        p.setMinIdle( dbProperties.getMinIdle());
        p.setMaxIdle( dbProperties.getMaxIdle());

        return p;
    }
}
