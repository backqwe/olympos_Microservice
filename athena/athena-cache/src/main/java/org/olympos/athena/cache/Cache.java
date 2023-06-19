package org.olympos.athena.cache;

/**
 * 缓存对象接口
 * 
 * @author <a href="mailto:shiban@microants.cn">石斑</a>
 * @version 1.0.0 2016年12月14日
 * @since 1.0.0
 */
public interface Cache {
    /**
     * 设置简单的字符串类型的key-value键值对，无过期时间
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 设置简单的带过期时间的字符串类型的key-value键值对
     * 
     * @param key
     * @param value
     * @param seconds
     */
    public void setex(String key, String value, int seconds);

    /**
     * 获取指定字符串类型key的值
     * 
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 删除指定字符串类型key的缓存
     * 
     * @param key
     * @return
     */
    public Long del(String key);

    /**
     * 检查指定字符串类型key的缓存是否存在
     * 
     * @param key
     * @return true-存在、false-不存在
     */
    public Boolean exists(String key);

    /**
     * 设置指定字符串类型key的缓存过期时间
     * 
     * @param key
     * @param seconds
     * @return true-设置成功、false-设置失败
     */
    public Boolean expire(String key, int seconds);

    /**
     * 指定字符串类型key的缓存值自增（原子操作）
     * 
     * @param key
     * @return 自增后的值
     */
    public Long incr(String key);

    /**
     * 指定字符串类型key的缓存值自减（原子操作）
     * 
     * @param key
     * @return 自减后的值
     */
    public Long decr(String key);
}
