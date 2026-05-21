package com.simeon.picoinspector.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class CsvExporter {

    public void exportValues(float[] values, String outputPath) throws IOException {
        Path path = Path.of(outputPath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        StringBuilder csv = new StringBuilder();
        csv.append("index,value,is_nan\n");

        for (int i = 0; i < values.length; i++) {
            float value = values[i];
            boolean isNan = Float.isNaN(value);

            csv.append(i)
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
