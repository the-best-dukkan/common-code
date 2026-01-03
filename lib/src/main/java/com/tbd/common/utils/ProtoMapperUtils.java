package com.tbd.common.utils;

import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProtoMapperUtils {

    public Timestamp mapInstantToTimestamp(Instant instant) {
        if (instant == null) return null;
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public Instant mapTimestampToInstant(Timestamp timestamp) {
        if (timestamp == null || (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0)) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}