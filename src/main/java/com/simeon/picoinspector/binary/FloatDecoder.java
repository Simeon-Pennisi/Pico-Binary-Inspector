package com.simeon.picoinspector.binary;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FloatDecoder {

    public void printFirstFloat32Values(byte[] data, int count) {
        System.out.println("---- First " + count + " Float32 Values ----");

        int availableValues = data.length / 4;
        if (availableValues < count) {
            System.out.println("Warning: Only " + availableValues + " float32 values available in data.");
            count = availableValues;
        }

        int limit = Math.min(count, availableValues);

        System.out.println("Big-endian:");
        
        for (int i = 0; i < limit; i++) {
            float value = ByteBuffer.wrap(data, i * 4, 4)
            .order(ByteOrder.BIG_ENDIAN)
            .getFloat();

            System.out.printf("[%d] %f%n", i, value);
        }
        
        System.out.println("---------------------------------------");

        System.out.println("Little-endian:");
        for (int i = 0; i < limit; i++) {
            float value = ByteBuffer.wrap(data, i * 4, 4)
            .order(ByteOrder.LITTLE_ENDIAN)
            .getFloat();

            System.out.printf("[%d] %f%n", i, value);
        }

        System.out.println("---------------------------------------");
    }
}
