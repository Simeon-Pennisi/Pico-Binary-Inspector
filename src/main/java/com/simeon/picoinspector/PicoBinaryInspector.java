package com.simeon.picoinspector;

import com.simeon.picoinspector.http.PicoBlockClient;
import com.simeon.picoinspector.binary.BinaryFileWriter;
import com.simeon.picoinspector.binary.BinaryStats;
import com.simeon.picoinspector.binary.DecodeStats;
import com.simeon.picoinspector.binary.HexDumpFormatter;
import com.simeon.picoinspector.binary.FloatDecoder;
import com.simeon.picoinspector.export.CsvExporter;
import java.util.Arrays;

public class PicoBinaryInspector {
    public static void main(String[] args) {
        System.out.println("PicoBinaryInspector started");
        System.out.println("Arguments received: " + Arrays.toString(args));

            if (args.length == 0) {
                System.out.println("Usage:");
                System.out.println("  java PicoBinaryInspector <block-url>");
                return;
            }

            String blockUrl = args[0];
            System.out.println("Inspecting block at URL: " + blockUrl);
            PicoBlockClient blockClient = new PicoBlockClient();
            try {
                byte[] data = blockClient.fetchBlockData(blockUrl);
                System.out.println("Downloaded bytes: " + data.length);
                // Additional processing of data done here
                String outputPath = "data/raw/block.bin";

                BinaryFileWriter fileWriter = new BinaryFileWriter();
                fileWriter.write(data, outputPath);
                System.out.println("Binary file saved to: " + outputPath);

                BinaryStats stats = new BinaryStats();
                HexDumpFormatter hexDump = new HexDumpFormatter();
                stats.printStats(data);
                hexDump.printFirstBytes(data, 128);

                FloatDecoder floatDecoder = new FloatDecoder();
                floatDecoder.printFirstFloat32Values(data, 20);

                float[] values = floatDecoder.decodeBigEndianFloat32Array(data);

                DecodeStats decodeStats = new DecodeStats();
                decodeStats.printFloatStats(values);

            CsvExporter csvExporter = new CsvExporter();
            String csvPath = "data/decoded/block.csv";
            csvExporter.exportValues(values, csvPath);

            System.out.println("Decoded CSV saved to: " + csvPath);

            } catch (Exception e) {
                System.err.println("Error fetching block data: " + e.getMessage());
                e.printStackTrace();
            }
    }
}