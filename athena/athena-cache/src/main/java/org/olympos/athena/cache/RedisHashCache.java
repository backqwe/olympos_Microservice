package org.olympos.athena.cache;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * RedisHashCache
 *
 * @Describe: todo 还需要添加方法
 * @Author Chasen Hao
 * @Date 2023-07-29 21:39
 */
@Service
public class RedisHashCache extends BaseRedisCache {

    private static HashOperations<String, String, Serializable> opsForHash = null;

    private synchronized <T extends Serializable> HashOperations<String, String, T> getOpsForHash() {
        if ( Objects.isNull( opsForHash))
            opsForHash = this.getRedisCacheTemplate().opsForHash();
        return (HashOperations<String, String, T>) opsForHash;
    }

    /**
     * 放置一个键值对
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void put(String key, String hashKey, Serializable value) {
        this.getOpsForHash().put( key, hashKey, value);
    }

    /**
     * 放置一个map
     *
     * @param key
     * @param map
     */
    public void putAll(String key, Map<String,? extends Serializable> map) {
        this.getOpsForHash().putAll( key, map);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @param hashKey
     * @return
     * @param <T>
     */
    public <T extends Serializable> T get(String key, String hashKey) {
        return (T)this.getOpsForHash().get( key, hashKey);
    }

    /**
     * 获取多个值
     *
     * @param key
     * @param fields
     * @return
     * @param <T>
     */
    public <T extends Serializable> List<T> multiGet(String key, Collection<String> fields) {
        return (List<T>) this.getOpsForHash().multiGet( key, fields);
    }

    /**
     * 获取全部键值对
     *
     * @param key
     * @return
     * @param <T>
     */
    public <T extends Serializable> Map<String, T>  getAll(String key) {
        return (Map<String, T>) this.getOpsForHash().entries( key);
    }

    /**
     * 删除多个键值对
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public long delHashes(String key, Object... hashKeys) {
        return this.getOpsForHash().delete( key, hashKeys);
    }

    /**
     * 是否包含键值对
     *
     * @param key
     * @param hashKey
     * @return
     */
    public boolean contains(String key, String hashKey) {
        return this.getOpsForHash().hasKey( key, hashKey);
    }

    /**
     * 获取所有关键字
     *
     * @param key
     * @return
     */
    public Set<String> hashKeys(String key) {
        return this.getOpsForHash().keys( key);
    }

    /**
     * 获取迭代器
     *
     * @param key
     * @return
     */
    public Cursor<Map.Entry<String, Serializable>> iterator(String key) {
        return this.getOpsForHash().scan( key, ScanOptions.scanOptions().build());
    }
}
