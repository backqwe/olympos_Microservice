package org.olympos.athena.cache;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * RedisSetCache
 *
 * @Describe: todo 还需要添加方法
 * @Author Chasen Hao
 * @Date 2023-07-29 21:39
 */
@Service
public class RedisSetCache extends BaseRedisCache {

    private static SetOperations<String, Serializable> opsForSet = null;

    private synchronized SetOperations<String, Serializable> getOpsForSet() {
        if ( Objects.isNull( opsForSet))
            opsForSet = this.getRedisCacheTemplate().opsForSet();
        return opsForSet;
    }

    /**
     * 添加元素
     *
     * @param key
     * @param values
     * @return
     */
    public long add(String key, Serializable... values){
        return this.getOpsForSet().add( key, values);
    }

    /**
     * 移除元素
     *
     * @param key
     * @param value
     * @return
     */
    public long remove(String key, Serializable value){
        return this.getOpsForSet().remove( key, value);
    }

    /**
     * 获取全部元素
     *
     * @param key
     * @return
     */
    public <T extends Serializable> Set<T> getAll(String key){
        return (Set<T>)this.getOpsForSet().members( key);
    }

    /**
     * 是否包含元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean contains(String key, Serializable value){
        return this.getOpsForSet().isMember( key, value);
    }

    /**
     * 获取容量
     *
     * @param key
     * @return
     */
    public long size(String key){
        return this.getOpsForSet().size( key);
    }
}
