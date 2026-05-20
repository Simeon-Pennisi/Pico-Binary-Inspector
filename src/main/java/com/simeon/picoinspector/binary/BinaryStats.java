package com.simeon.picoinspector.binary;

public class BinaryStats {
    
    public void printStats(byte[] data) {
        int length = data.length;

        System.out.println("---- Binary Stats ----");
        System.out.println("Total bytes: " + length);
        System.out.println("Divisibility by 4: " + (length % 4 == 0 ? "Yes" : "No"));
        System.out.println("Divisibility by 8: " + (length % 8 == 0 ? "Yes" : "No"));
        System.out.println("Possible float32 values: " + (length / 4));
        System.out.println("Possible float64 values: " + (length / 8));
        System.out.println("----------------------");
    }
}
