package org.olympos.athena.cache;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * RedisValueCache
 *
 * @Describe: todo 还需要添加方法
 * @Author Chasen Hao
 * @Date 2023-07-29 21:39
 */
@Service
public class RedisValueCache extends BaseRedisCache {

    private static ValueOperations<String, Serializable> opsForValue = null;

    private synchronized ValueOperations<String, Serializable> getOpsForValue() {
        if ( Objects.isNull( opsForValue))
            opsForValue = this.getRedisCacheTemplate().opsForValue();
        return opsForValue;
    }

    /**
     * 设置一个key
     *
     * @param key
     * @param value
     */
    public void set(String key, Serializable value) {
        this.set( key, value, -1);
    }

    /**
     * 设置一个会过期的key
     *
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Serializable value, long expire) {

        if ( Objects.isNull( key) || key.trim().isEmpty()) throw new RuntimeException("key 不能为空");

        // 数据不过期
        if ( expire <= 0)
            this.getOpsForValue().set( key, value);
        else
            this.getOpsForValue().set( key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 当不存在此key时才会设置key
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Serializable value) {
        Boolean var = this.getOpsForValue().setIfAbsent( key, value);
        return Objects.nonNull( var) && var;
    }

    /**
     * 获取key所带来的value
     *
     * @param key
     * @return
     * @param <T>
     */
    public  <T extends Serializable> T get(String key) {
        return (T) this.getOpsForValue().get( key);
    }
}
