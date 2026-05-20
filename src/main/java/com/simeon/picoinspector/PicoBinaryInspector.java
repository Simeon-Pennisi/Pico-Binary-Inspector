package com.simeon.picoinspector;

import com.simeon.picoinspector.http.PicoBlockClient;
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
                byte[] blockData = blockClient.fetchBlockData(blockUrl);
                System.out.println("Downloaded bytes: " + blockData.length);
                // Additional processing of blockData can be done here
            } catch (Exception e) {
                System.err.println("Error fetching block data: " + e.getMessage());
                e.printStackTrace();
            }
    }
}