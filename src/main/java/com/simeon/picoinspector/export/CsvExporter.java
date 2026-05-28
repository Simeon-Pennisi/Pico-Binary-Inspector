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
        long intervalMs,
        String channel_Id_raw,
        String channel_Id_decoded
    ) throws IOException {
        Path path = Path.of(csvPath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        StringBuilder csv = new StringBuilder();
        csv.append("index,timestamp_utc,value,is_nan,channel_Id_raw,channel_Id_decoded\n");

        for (int i = 0; i < values.length; i++) {
            float value = values[i];
            boolean isNan = Float.isNaN(value);

            TimestampReconstructor reconstructor = new TimestampReconstructor();
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
                    .append(",");


            csv.append(channel_Id_raw)
                    .append(",");
            csv.append(channel_Id_decoded)
                    .append("\n");
            
        }

        Files.writeString(path, csv.toString());
    }
}
