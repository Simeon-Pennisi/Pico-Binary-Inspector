package com.simeon.picoinspector.binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BinaryFileWriter {

    public void write(byte[] data, String outputPath) throws IOException {
        Path path = Path.of(outputPath);
        
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Files.write(path, data);
    }
}