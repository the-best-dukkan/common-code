package com.tbd.common.utils;

import com.google.protobuf.Message;
import com.tbd.common.cache.ProtobufRedisSerializer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

public class RedisUtil {

    private RedisUtil() {}

    public static <T extends com.google.protobuf.Message> RedisCacheConfiguration createProtoCacheConfig(
            Class<T> messageType,
            Duration ttl,
            RedisSerializationContext.SerializationPair<String> keySerializer) {

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new ProtobufRedisSerializer<>(messageType)
                        )
                )
                .entryTtl(ttl);
    }

    public static  <T extends Message> RedisTemplate<String, T> createProtoTemplate(RedisConnectionFactory factory, Class<T> clazz) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new ProtobufRedisSerializer<>(clazz));
        return template;
    }
}
