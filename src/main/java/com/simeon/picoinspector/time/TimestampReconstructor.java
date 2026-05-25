package com.simeon.picoinspector.time;

import java.time.Instant;

public class TimestampReconstructor {

    public Instant reconstructTimestamp(long blockStartTimeMs, int index, long intervalMs) {
        return Instant.ofEpochMilli(blockStartTimeMs + (index * intervalMs));
    }
}