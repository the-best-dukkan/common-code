package com.tbd.common.cache;

import com.google.protobuf.Message;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class ProtobufRedisSerializer<T extends Message> implements RedisSerializer<T> {

    private final Class<T> type;
    private final Method parseFromMethod;

    public ProtobufRedisSerializer(Class<T> type) {
        this.type = type;
        try {
            // Every Protobuf class has a static parseFrom(byte[]) method
            this.parseFromMethod = type.getMethod("parseFrom", byte[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Invalid Protobuf class", e);
        }
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable T value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        return value.toByteArray();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (T) parseFromMethod.invoke(null, (Object) bytes);
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize Protobuf message of type " + type, e);
        }
    }
}
