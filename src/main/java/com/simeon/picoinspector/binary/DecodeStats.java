package com.simeon.picoinspector.binary;

public class DecodeStats {

    public void printFloatStats(float[] values) {
        int total = values.length;
        int nanCount = 0;
        int validCount = 0;

        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        double sum = 0.0;

        for (float value : values) {
            if (Float.isNaN(value)) {
                nanCount++;
                continue;
            }
            validCount++;
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }

        System.out.println("---- Decode Stats ----");
        System.out.println("Total values: " + total);
        System.out.println("Valid values: " + validCount);
        System.out.println("NaN values: " + nanCount);
        if (validCount > 0) {
            System.out.println("Min value: " + min);
            System.out.println("Max value: " + max);
            System.out.println("Average value: " + (sum / validCount));
        } else {
            System.out.println("Min value: N/A");
            System.out.println("Max value: N/A");
            System.out.println("Average value: N/A");
        }

        System.out.println("-----------------------");
    }
}
