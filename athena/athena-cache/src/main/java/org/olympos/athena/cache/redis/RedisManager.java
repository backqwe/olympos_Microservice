package org.olympos.athena.cache.redis;

import jakarta.annotation.Resource;
import org.olympos.athena.cache.util.CacheKey;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisManager
 * @Author haochangsheng
 * @Data 2019/6/315:36
 * @Description
 */
@Service("redisManager")
public class RedisManager {

    @Resource
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    private static HashOperations<String, String, Serializable> opsForHash = null;
    private static ZSetOperations<String, Serializable> opsForZSet = null;
    private static SetOperations<String, Serializable> opsForSet = null;
    private static ValueOperations<String, Serializable> opsForValue = null;

    private synchronized <T extends Serializable> HashOperations<String, String, T> getOpsForHash() {
        if ( null == opsForHash)
            opsForHash = redisCacheTemplate.opsForHash();
        return (HashOperations<String, String, T>) opsForHash;
    }

    private synchronized ZSetOperations<String, Serializable> getOpsForZSet() {
        if ( null == opsForZSet)
            opsForZSet = redisCacheTemplate.opsForZSet();
        return opsForZSet;
    }

    private synchronized SetOperations<String, Serializable> getOpsForSet() {
        if ( null == opsForSet)
            opsForSet = redisCacheTemplate.opsForSet();
        return opsForSet;
    }

    private synchronized ValueOperations<String, Serializable> getOpsForValue() {
        if ( null == opsForValue)
            opsForValue = redisCacheTemplate.opsForValue();
        return opsForValue;
    }

    /** generic **/

    public Set<String> keys(CacheKey keyPattern) { return this.keys( keyPattern.getKey()); }
    public Set<String> keys(String keyPattern) {
        return redisCacheTemplate.keys( keyPattern);
    }

    public boolean hasKey(CacheKey key) {
        return this.hasKey( key.getKey());
    }
    public boolean hasKey(String key) {
        return redisCacheTemplate.hasKey( key);
    }

    public void expire(CacheKey key, long expire) {
        this.expire( key.getKey(), expire);
    }
    public void expire(String key, long expire) {
        redisCacheTemplate.expire( key, expire, TimeUnit.SECONDS);
    }

    public void expireAt(CacheKey key, Date date) {
        this.expireAt( key.getKey(), date);
    }
    public void expireAt(String key, Date date) {
        redisCacheTemplate.expireAt( key, date);
    }

    public long getExpire(CacheKey key) {
        return this.getExpire( key.getKey());
    }
    public long getExpire(String key) {
        Long expire = redisCacheTemplate.getExpire( key);
        if ( null == expire) return 0L;

        return expire.longValue();
    }

    public void delBatch(CacheKey pattern) {
        this.delBatch( pattern.getKey());
    }
    public void delBatch(String pattern) {
        redisCacheTemplate.delete( redisCacheTemplate.keys( pattern));
    }

    public void del(CacheKey key) {
        this.del( key.getKey());
    }
    public void del(String key) {
        redisCacheTemplate.delete( key);
    }

    /** keyValue **/

    public void put(CacheKey key, Serializable value) {
        this.put( key.getKey(), value, -1);
    }
    public void put(String key, Serializable value) {
        this.put( key, value, -1);
    }

    public void put(CacheKey key, Serializable value, long expire) {
        this.put( key.getKey(), value, expire);
    }
    public void put(String key, Serializable value, long expire) {

        if ( null == key || key.trim().isEmpty()) throw new RuntimeException("key 不能为空");

        // 数据不过期
        if ( expire <= 0)
            this.getOpsForValue().set( key, value);
        else
            this.getOpsForValue().set( key, value, expire, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T get(CacheKey key) {
        return this.get( key.getKey());
    }
    public  <T extends Serializable> T get(String key) {
        return (T) this.getOpsForValue().get( key);
    }

    public boolean setIfAbsent(CacheKey key, Serializable value) {
        return this.setIfAbsent( key.getKey(), value);
    }
    public boolean setIfAbsent(String key, Serializable value) {
        return this.getOpsForValue().setIfAbsent( key, value);
    }

    /** set **/

    public long setAdd(CacheKey key, Serializable... values){
        return this.setAdd( key.getKey(), values);
    }
    public long setAdd(String key, Serializable... values){
        return this.getOpsForSet().add( key, values);
    }

    public Set<Serializable> setGetAll(CacheKey key){
        return this.setGetAll( key.getKey());
    }
    public Set<Serializable> setGetAll(String key){
        return this.getOpsForSet().members( key);
    }

    public long setDel(CacheKey key, Serializable value){
        return this.setDel( key.getKey(), value);
    }
    public long setDel(String key, Serializable value){
        return this.getOpsForSet().remove( key, value);
    }

    public boolean setIsMember(CacheKey key, Serializable value){
        return this.setIsMember( key.getKey(), value);
    }
    public boolean setIsMember(String key, Serializable value){
        return this.getOpsForSet().isMember( key, value);
    }

    public long setSize(CacheKey key){
        return this.setSize( key.getKey());
    }
    public long setSize(String key){
        return this.getOpsForSet().size( key);
    }

    /** hash **/

    public void hashPut(CacheKey key, String hashKey, Serializable value) {
        this.hashPut( key.getKey(), hashKey, value);
    }
    public void hashPut(String key, String hashKey, Serializable value) {
        this.getOpsForHash().put( key, hashKey, value);
    }

    public void hashPut(CacheKey key, Map<String,Serializable> map) {
        this.hashPut( key.getKey(), map);
    }
    public void hashPut(String key, Map<String,Serializable> map) {
        this.getOpsForHash().putAll( key, map);
    }

    public <T extends Serializable> T hashGet(CacheKey key, String hashKey) {
        return this.hashGet( key.getKey(), hashKey);
    }
    public <T extends Serializable> T hashGet(String key, String hashKey) {
        return (T)this.getOpsForHash().get( key, hashKey);
    }

    public <T extends Serializable> List<T> hashMultiGet(CacheKey key, Collection<String> fields) {
        return this.hashMultiGet( key.getKey(), fields);
    }
    public <T extends Serializable> List<T> hashMultiGet(String key, Collection<String> fields) {
        return (List<T>) this.getOpsForHash().multiGet( key, fields);
    }

    public <T extends Serializable> Map<String, T>  hashgetAll(CacheKey poolKey) {
        return this.hashgetAll( poolKey.getKey());
    }
    public <T extends Serializable> Map<String, T>  hashgetAll(String poolKey) {
        return (Map<String, T>) this.getOpsForHash().entries( poolKey);
    }

    public Cursor<Map.Entry<String, Serializable>> hashScan(CacheKey key) {
        return this.hashScan( key.getKey());
    }
    public Cursor<Map.Entry<String, Serializable>> hashScan(String key) {
        return this.getOpsForHash().scan( key, ScanOptions.scanOptions().build());
    }

    public long hashDel(CacheKey key, Object... hashKeys) {
        return this.hashDel( key.getKey(), hashKeys);
    }
    public long hashDel(String key, Object... hashKeys) {
        return this.getOpsForHash().delete( key, hashKeys);
    }

    public boolean hashHasKey(CacheKey key, String hashKey) {
        return this.hashHasKey( key.getKey(), hashKey);
    }
    public boolean hashHasKey(String key, String hashKey) {
        return this.getOpsForHash().hasKey( key, hashKey);
    }

    public Set<String> hashKeys(CacheKey key) {
        return this.hashKeys( key.getKey());
    }
    public Set<String> hashKeys(String key) {
        return this.getOpsForHash().keys( key);
    }

    /** zset **/

    public Boolean zsetAdd(CacheKey key, Serializable value) {
        return this.zsetAdd( key.getKey(), value);
    }
    public Boolean zsetAdd(String key, Serializable value) {
        return this.getOpsForZSet().add( key, value, 0.1D);
    }

    public Boolean zsetAdd(CacheKey key, Serializable value, final double score) {
        return this.zsetAdd( key.getKey(), value, score);
    }
    public Boolean zsetAdd(String key, Serializable value, final double score) {
        return this.getOpsForZSet().add( key, value, score);
    }

    public Long zsetAdd(CacheKey key, Set<ZSetOperations.TypedTuple<Serializable>> tuples) {
        return this.zsetAdd( key.getKey(), tuples);
    }
    public Long zsetAdd(String key, Set<ZSetOperations.TypedTuple<Serializable>> tuples) {
        return this.getOpsForZSet().add( key, tuples);
    }

    public Boolean zsetAddSortDesc(CacheKey key, Serializable value) {
        return this.zsetAddSortDesc( key.getKey(), value);
    }
    public Boolean zsetAddSortDesc(String key, Serializable value) {
        Set values = this.getOpsForZSet().reverseRange( key, 0, 1);
        Double score = null;
        if ( null != values && values.size() != 0)
            score = this.getOpsForZSet().score( key, values.toArray()[0]) + 0.1D;
        return this.getOpsForZSet().add( key, value, null == score ? 0.1D : score);
    }

    public Long zsetRemove(CacheKey key, Object... values) {
        return this.zsetRemove( key.getKey(), values);
    }
    public Long zsetRemove(String key, Object... values) {
        return this.getOpsForZSet().remove( key, values);
    }

    public Set<Serializable> zsetFindInPage(CacheKey key, int pageSize, int pageNo) {
        return this.zsetFindInPage( key.getKey(), pageSize, pageNo);
    }
    // todo rangeByLex修改
    public Set<Serializable> zsetFindInPage(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().rangeByLex( key,
                Range.unbounded(),
                Limit.limit().count( pageSize).offset( pageSize*( pageNo-1)));
    }

    public Set<Serializable> zsetFindInPageSortDesc(CacheKey key, int pageSize, int pageNo) {
        return this.zsetFindInPageSortDesc( key.getKey(), pageSize, pageNo);
    }
    public Set<Serializable> zsetFindInPageSortDesc(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().reverseRange( key, pageSize*( pageNo-1), pageSize*( pageNo) - 1);
    }

    public Set<Serializable> zsetFindInPageSortAsc(CacheKey key, int pageSize, int pageNo) {
        return this.zsetFindInPageSortAsc( key.getKey(), pageSize, pageNo);
    }
    public Set<Serializable> zsetFindInPageSortAsc(String key, int pageSize, int pageNo) {
        return this.getOpsForZSet().range( key, pageSize*( pageNo-1), pageSize*( pageNo));
    }

    public Set<Serializable> zsetFindAll(CacheKey key) {
        return this.zsetFindAll( key.getKey());
    }
    public Set<Serializable> zsetFindAll(String key) {
        return this.getOpsForZSet().rangeByLex( key,
                Range.unbounded());
    }

    public Set<Serializable> zsetFindAllSortDesc(CacheKey key) {
        return this.zsetFindAllSortDesc( key.getKey());
    }
    public Set<Serializable> zsetFindAllSortDesc(String key) {
        return this.getOpsForZSet().reverseRange( key, 0, -1);
    }

    public Set<Serializable> zsetFindAllSortAsc(CacheKey key) {
        return this.zsetFindAllSortAsc( key.getKey());
    }
    public Set<Serializable> zsetFindAllSortAsc(String key) {
        return this.getOpsForZSet().range( key, 0, -1);
    }

    public Cursor<ZSetOperations.TypedTuple<Serializable>> zsetFindAllInIterator(CacheKey key) {
        return this.zsetFindAllInIterator( key.getKey());
    }
    public Cursor<ZSetOperations.TypedTuple<Serializable>> zsetFindAllInIterator(String key) {
        return this.getOpsForZSet().scan( key, ScanOptions.scanOptions().build());
    }

    public Set<Serializable> zsetRangeByLex(CacheKey key, Range<String> range, RedisZSetCommands.Limit limit) {
        return this.zsetRangeByLex( key.getKey(), range, limit);
    }
    public Set<Serializable> zsetRangeByLex(String key, Range<String> range, RedisZSetCommands.Limit limit) {
        return this.getOpsForZSet().rangeByLex( key, range, limit);
    }

    public Set<Serializable> zsetRangeByLex(CacheKey key, Range<String> range) {
        return this.zsetRangeByLex( key.getKey(), range);
    }
    public Set<Serializable> zsetRangeByLex(String key, Range<String> range) {
        return this.getOpsForZSet().rangeByLex( key, range);
    }

    public Set<Serializable> zsetReverseRange(CacheKey key, long start, long end) {
        return this.zsetReverseRange( key.getKey(), start, end);
    }
    public Set<Serializable> zsetReverseRange(String key, long start, long end) {
        return this.getOpsForZSet().reverseRange( key, start, end);
    }

    public Double zsetIncrementScore(CacheKey key, Serializable value, double increment) {
        return this.zsetIncrementScore( key.getKey(), value, increment);
    }
    public Double zsetIncrementScore(String key, Serializable value, double increment) {
        return this.getOpsForZSet().incrementScore( key, value, increment);
    }

    public Double zsetScore(CacheKey key, Object value) {
        return this.zsetScore( key.getKey(), value);
    }
    public Double zsetScore(String key, Object value) {
        return this.getOpsForZSet().score( key, value);
    }

    public Long zsetCount(CacheKey key, double minScore, double maxScore) {
        return this.zsetCount( key.getKey(), minScore, maxScore);
    }
    public Long zsetCount(String key, double minScore, double maxScore) {
        return this.getOpsForZSet().count( key, minScore, maxScore);
    }

    public Long zsetSize(CacheKey key) {
        return this.zsetSize( key.getKey());
    }
    public Long zsetSize(String key) {
        return this.getOpsForZSet().size( key);
    }
}
