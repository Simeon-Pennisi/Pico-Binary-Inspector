package com.simeon.picoinspector;

import com.simeon.picoinspector.http.PicoBlockClient;
import com.simeon.picoinspector.time.BlockChannelIdParser;
import com.simeon.picoinspector.time.BlockUrlParser;
import com.simeon.picoinspector.binary.BinaryFileWriter;
import com.simeon.picoinspector.binary.BinaryStats;
import com.simeon.picoinspector.binary.DecodeStats;
import com.simeon.picoinspector.binary.HexDumpFormatter;
import com.simeon.picoinspector.binary.FloatDecoder;
import com.simeon.picoinspector.export.CsvExporter;
import com.simeon.picoinspector.time.CaptureIdParser;
import com.simeon.picoinspector.time.ResolutionParser;

import java.util.Arrays;

public class PicoBinaryInspector {
    public static void main(String[] args) {
        System.out.println("PicoBinaryInspector started");
        System.out.println("Arguments received: " + Arrays.toString(args));

            if (args.length == 0) {
                System.out.println("Usage:");
                System.out.println("  java PicoBinaryInspector <block-url> <interval-ms>");
                return;
            }

            String blockUrl = args[0];
            System.out.println("Inspecting block at URL: " + blockUrl);
            PicoBlockClient blockClient = new PicoBlockClient();

            BlockChannelIdParser channelIdParser = new BlockChannelIdParser();
            String channel_Id_raw = channelIdParser.extractChannelId(blockUrl);
            String channel_Id_decoded = java.net.URLDecoder.decode(channel_Id_raw, java.nio.charset.StandardCharsets.UTF_8);
            // System.out.println("Encoded channel ID: " + channel_Id_raw);
            // System.out.println("Decoded channel ID: " + channel_Id_decoded);

            // BlockUrlParser parser = new BlockUrlParser();
            // long blockStartEpochMs = parser.extractBlockStartEpochMs(blockUrl);
            // System.out.println("blockUrl: " + blockUrl);
            // System.out.println("blockStartEpochMs: " + blockStartEpochMs);
            // System.out.println("intervalMs: " + intervalMs);

            CaptureIdParser captureIdParser = new CaptureIdParser();
            String captureId = captureIdParser.extractCaptureId(blockUrl);
            // System.out.println("Capture ID: " + captureId);

            ResolutionParser resolutionParser = new ResolutionParser();
            String resolution = String.valueOf(resolutionParser.extractResolution(blockUrl));
            // System.out.println("Resolution: " + resolution);

            long resolutionSeconds = resolutionParser.extractResolution(blockUrl);
            long intervalMs = resolutionSeconds * 1000L; // Default to 1 second

            BlockUrlParser parser = new BlockUrlParser();
            long blockStartEpochMs = parser.extractBlockStartEpochMs(blockUrl);
            // System.out.println("blockUrl: " + blockUrl);
            // System.out.println("blockStartEpochMs: " + blockStartEpochMs);
            // System.out.println("intervalMs: " + intervalMs);

            if (args.length >= 2) {
                try {
                    intervalMs = Long.parseLong(args[1]);
                    if (intervalMs <= 0) {
                        System.err.println("Interval must be positive: " + intervalMs);
                        return;
                    }
                    System.out.println("Using custom intervalMs: " + intervalMs);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid intervalMs provided: " + intervalMs);
                    return;
                }
            }
    
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

            // BlockChannelIdParser channelIdParser = new BlockChannelIdParser();
            // String channel_Id_raw = channelIdParser.extractChannelId(blockUrl);
            // String channel_Id_decoded = java.net.URLDecoder.decode(channel_Id_raw, java.nio.charset.StandardCharsets.UTF_8);
            System.out.println("Encoded channel ID: " + channel_Id_raw);
            System.out.println("Decoded channel ID: " + channel_Id_decoded);

            // BlockUrlParser parser = new BlockUrlParser();
            // long blockStartEpochMs = parser.extractBlockStartEpochMs(blockUrl);
            System.out.println("blockUrl: " + blockUrl);
            System.out.println("blockStartEpochMs: " + blockStartEpochMs);
            System.out.println("intervalMs: " + intervalMs);

            // CaptureIdParser captureIdParser = new CaptureIdParser();
            // String captureId = captureIdParser.extractCaptureId(blockUrl);
            System.out.println("Capture ID: " + captureId);

            // ResolutionParser resolutionParser = new ResolutionParser();
            // String resolution = String.valueOf(resolutionParser.extractResolution(blockUrl));
            System.out.println("Resolution: " + resolution);

            CsvExporter csvExporter = new CsvExporter();
            String csvPath = "data/decoded/block.csv";
            csvExporter.exportValues(
                values, 
                csvPath, 
                blockStartEpochMs, 
                intervalMs,
                channel_Id_raw,
                channel_Id_decoded,
                captureId,
                resolution
            );

            System.out.println("Decoded CSV saved to: " + csvPath);

            } catch (Exception e) {
                System.err.println("Error fetching block data: " + e.getMessage());
                e.printStackTrace();
            }
    }
}