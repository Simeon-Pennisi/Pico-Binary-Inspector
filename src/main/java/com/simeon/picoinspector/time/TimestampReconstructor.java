package com.simeon.picoinspector.time;

import java.time.Instant;

public class TimestampReconstructor {

    public Instant reconstruct(long blockStartEpochMs, int index, long intervalMs) {
        return Instant.ofEpochMilli(blockStartEpochMs + (index * intervalMs));
    }
}