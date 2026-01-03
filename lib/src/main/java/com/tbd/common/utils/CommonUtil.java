package com.tbd.common.utils;

import com.google.protobuf.Message;
import com.tbd.common.cache.ProtobufRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CommonUtil {

    private CommonUtil() {}

    public static boolean validateId(Long id) {
        return id != null && id >= 1;
    }

    public static  <T extends Message> RedisTemplate<String, T> createProtoTemplate(RedisConnectionFactory factory, Class<T> clazz) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new ProtobufRedisSerializer<>(clazz));
        return template;
    }
}
