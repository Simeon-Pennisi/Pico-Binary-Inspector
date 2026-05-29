package com.simeon.picoinspector.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Locale;

import com.simeon.picoinspector.time.TimestampReconstructor;

public class CsvExporter {

    public void exportValues(
        // rename "raw_value" to appropriately reflect the type of data being exported, e.g. "millivolts" once confirmed
        float[] values,
        String csvPath,
        long blockStartEpochMs,
        long intervalMs,
        String channel_id_raw,
        String channel_id_decoded,
        String capture_id
    ) throws IOException {
        Path path = Path.of(csvPath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        StringBuilder csv = new StringBuilder();
        csv.append("index,timestamp_utc,value,is_nan,channel_id_raw,channel_id_decoded,capture_id\n");

        for (int i = 0; i < values.length; i++) {
            float raw_value = values[i];
            boolean isNan = Float.isNaN(raw_value);

            TimestampReconstructor reconstructor = new TimestampReconstructor();
            Instant timestamp = reconstructor.reconstruct(blockStartEpochMs, i, intervalMs);

            csv.append(i)
                    .append(",");
                    
            csv.append(timestamp.toString())
                    .append(",");

            if (isNan) {
                csv.append("NaN");
            } else {
                csv.append(String.format(Locale.US, "%.6f", raw_value));
            }

            csv.append(",")
                    .append(isNan)
                    .append(",");


            csv.append(channel_id_raw)
                    .append(",");
            csv.append(channel_id_decoded)
                    .append(",");

            csv.append(capture_id)
                    .append("\n");
            
        }

        Files.writeString(path, csv.toString());
    }
}
