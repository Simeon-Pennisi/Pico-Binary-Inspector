package com.simeon.picoinspector.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Locale;

import com.simeon.picoinspector.time.BlockUrlParser;
import com.simeon.picoinspector.time.TimestampReconstructor;

public class CsvExporter {

    public void exportValues(
        float[] values,
        String csvPath,
        long blockStartEpochMs,
        long intervalMs
    ) throws IOException {
        Path path = Path.of(csvPath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        StringBuilder csv = new StringBuilder();
        csv.append("index,timestamp_utc,value,is_nan\n");

        for (int i = 0; i < values.length; i++) {
            float value = values[i];
            boolean isNan = Float.isNaN(value);

            TimestampReconstructor reconstructor = new TimestampReconstructor();
            // blockStartEpochMs = values.length; // Placeholder for actual block start epoch ms
            // intervalMs = 1000L; // Placeholder for actual interval ms
            Instant timestamp = reconstructor.reconstruct(blockStartEpochMs, i, intervalMs);

            csv.append(i)
                    .append(",");
                    
            csv.append(timestamp.toString())
                    .append(",");

            if (isNan) {
                csv.append("NaN");
            } else {
                csv.append(String.format(Locale.US, "%.6f", value));
            }

            csv.append(",")
                    .append(isNan)
                    .append("\n");
        }

        Files.writeString(path, csv.toString());
    }
}
