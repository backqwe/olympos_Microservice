package org.olympos.athena.cache;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * RedisPageSetCache
 *
 * @Describe: todo 还需要添加方法
 * @Author Chasen Hao
 * @Date 2023-07-29 21:39
 */
@Service
public class RedisPageSetCache extends BaseRedisCache {

    private static ZSetOperations<String, Serializable> opsForZSet = null;

    private synchronized ZSetOperations<String, Serializable> getOpsForZSet() {
        if ( Objects.isNull( opsForZSet))
            opsForZSet = this.getRedisCacheTemplate().opsForZSet();
        return opsForZSet;
    }

    /**
     * 指定位置添加
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean add(String key, Serializable value, final double score) {
        return this.getOpsForZSet().add( key, value, score);
    }

    /**
     * 升序添加
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean addAsc(String key, Serializable value) {
        return this.getOpsForZSet().add( key, value, 0.1d);
    }

    /**
     * 升序批量添加
     *
     * @param key
     * @param set
     * @return
     */
/*    public <T> Long addAllAsc(String key, Set<T> set) {
        return this.getOpsForZSet().add( key,
                set.stream()
                        .map( v -> new DefaultTypedTuple( v, 0.1d))
                        .collect( Collectors.toSet()));
    }*/

    /**
     * 降序添加
     *
     * @param key
     * @param value
     * @return
     *
     * @Describe: 无法保证原子化
     */
    public Boolean addDesc(String key, Serializable value) {
        Set<Serializable> values = this.getOpsForZSet().reverseRange( key, 0, 1);
        Double score = null;
        if ( Objects.nonNull( values) && values.size() != 0) {
            Double var = this.getOpsForZSet().score( key, values.toArray()[0]);
            score = Objects.isNull( var) ? null : var + 0.1d;
        }

        return this.getOpsForZSet().add( key, value, null == score ? 0.1d : score);
    }

    /**
     * 移除元素
     *
     * @param key
     * @param values
     * @return
     */
    public Long remove(String key, Object... values) {
        return this.getOpsForZSet().remove( key, values);
    }

    /**
     * 范围内计数
     *
     * @param key
     * @param minScore
     * @param maxScore
     * @return
     */
    public Long count(String key, double minScore, double maxScore) {
        return this.getOpsForZSet().count( key, minScore, maxScore);
    }

    /**
     * 容量
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return this.getOpsForZSet().size( key);
    }

    /**
     * 单页查找
     *
     * @param key
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Set<Serializable> findInPage(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().rangeByLex( key,
                Range.unbounded(),
                Limit.limit().count( pageSize).offset( pageSize*( pageNo-1)));
    }

    /**
     * 单页降序查找
     *
     * @param key
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Set<Serializable> findInPageDesc(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().reverseRange( key, pageSize*( pageNo-1), pageSize*( pageNo) - 1);
    }

    /**
     * 单页升序查找
     *
     * @param key
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Set<Serializable> findInPageAsc(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().range( key, pageSize*( pageNo-1), pageSize*( pageNo));
    }

    /**
     * 查找全部
     *
     * @param key
     * @return
     */
    public Set<Serializable> findAll(String key) {
        return this.getOpsForZSet().rangeByLex( key,
                Range.unbounded());
    }

    /**
     * 降序查找全部
     *
     * @param key
     * @return
     */
    public Set<Serializable> findAllDesc(String key) {
        return this.getOpsForZSet().reverseRange( key, 0, -1);
    }

    /**
     * 升序查找全部
     *
     * @param key
     * @return
     */
    public Set<Serializable> findAllAsc(String key) {
        return this.getOpsForZSet().range( key, 0, -1);
    }

    /**
     * 自定义查找
     *
     * @param key
     * @param range
     * @param limit
     * @return
     */
    public Set<Serializable> findInRange(String key, Range<String> range, RedisZSetCommands.Limit limit) {
        return this.getOpsForZSet().rangeByLex( key, range, limit);
    }

    /**
     * 自定义查找
     *
     * @param key
     * @param range
     * @return
     */
    public Set<Serializable> findInRange(String key, Range<String> range) {
        return this.getOpsForZSet().rangeByLex( key, range);
    }

    /**
     * 查找范围内数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Serializable> findInRange(String key, long start, long end) {
        return this.getOpsForZSet().reverseRange( key, start, end);
    }

    /**
     * 改变key的位置
     *
     * @param key
     * @param value
     * @param increment
     * @return
     */
    public Double incrementScore(String key, Serializable value, double increment) {
        return this.getOpsForZSet().incrementScore( key, value, increment);
    }

    /**
     * 改变key的位置
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, Object value) {
        return this.getOpsForZSet().score( key, value);
    }

    /**
     * 获取迭代器
     *
     * @param key
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<Serializable>> iterator(String key) {
        return this.getOpsForZSet().scan( key, ScanOptions.scanOptions().build());
    }
}
