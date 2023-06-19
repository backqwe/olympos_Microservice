package org.olympos.athena.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * RedisCacheConfig
 *
 * @Author haochangsheng
 * @Data 2019/6/411:20
 * @Description
 */
@Configuration
public class RedisCacheConfig extends BaseRedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean("redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate() {

        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        // string序列化
        StringRedisSerializer stringSeial = new StringRedisSerializer();
        // json序列化
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(
                new ObjectMapper()
                        // 序列化域: field = get + set, 修饰范围: ANY = private + public
                        .setVisibility( PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                        // 序列化输入类型: !final (ex: String, Integer)
                        .activateDefaultTyping(
                                BasicPolymorphicTypeValidator.builder().build(),
                                ObjectMapper.DefaultTyping.NON_FINAL)
                        // 序列化场合: !null
                        .setSerializationInclusion( JsonInclude.Include.NON_NULL)
                , Object.class);

        template.setConnectionFactory( redisConnectionFactory);
        template.setKeySerializer( stringSeial);
        template.setValueSerializer( jacksonSeial);
        template.setHashKeySerializer( stringSeial);
        template.setHashValueSerializer( jacksonSeial);

        return template;
    }
}
