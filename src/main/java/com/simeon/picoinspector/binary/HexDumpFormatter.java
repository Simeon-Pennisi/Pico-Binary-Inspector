package com.simeon.picoinspector.binary;

public class HexDumpFormatter {

    public void printFirstBytes(byte[] data, int maxBytes) {
        System.out.println("---- Hex Dump (First " + maxBytes + " bytes) ----");

        int limit = Math.min(maxBytes, data.length);
        
        for (int i = 0; i < limit; i++) {
            System.out.printf("%02X ", data[i]);

            if ((i + 1) % 16 == 0) {
                System.out.println();
            }
        }

        if (limit % 16 != 0) {
            System.out.println();
        }
        
        System.out.println("\n---------------------------------------");
    }
}