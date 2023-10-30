package org.olympos.athena.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * BaseRedisCache
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-07-29 21:38
 */
@Service
public class BaseRedisCache {

    @Resource
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    public RedisTemplate<String, Serializable> getRedisCacheTemplate() {
        return redisCacheTemplate;
    }

    /**
     * 删除key
     * delete key
     *
     * @param key
     */
    public void del(String key) {
        this.getRedisCacheTemplate().delete( key);
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void delBatch(String pattern) {
        Set<String> var = this.getRedisCacheTemplate().keys( pattern);
        if ( Objects.nonNull( var))
            this.getRedisCacheTemplate().delete( var);
    }

    /**
     * 获取所有key
     *
     * @param keyPattern
     * @return
     */
    public Set<String> keys(String keyPattern) {
        return this.getRedisCacheTemplate().keys( keyPattern);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        Boolean var = this.getRedisCacheTemplate().hasKey(key);
        return Objects.nonNull( var) && var;
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param expire
     */
    public void expire(String key, long expire) {
        this.getRedisCacheTemplate().expire( key, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置key的国企日期
     *
     * @param key
     * @param date
     */
    public void expireAt(String key, Date date) {
        this.getRedisCacheTemplate().expireAt( key, date);
    }

    /**
     * 获取ket的过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        Long expire = this.getRedisCacheTemplate().getExpire( key);
        if ( Objects.isNull( expire)) return 0L;

        return expire;
    }
}
