package com.simeon.picoinspector.binary;

public class DecodeStats {

    public void printFloatStats(float[] values) {
        int total = values.length;
        int nanCount = 0;
        int validCount = 0;
        int currentNanRun = 0;
        int maxNanRun = 0;
        int firstValidIndex = -1;
        int lastValidIndex = -1;
        int firstNanIndex = -1;

        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        double sum = 0.0;

        for (int i = 0; i < values.length; i++) {
            float value = values[i];            
            if (Float.isNaN(value)) {
                nanCount++;
                if (firstNanIndex == -1) {
                    firstNanIndex = i;
                }

                currentNanRun++;
                if (currentNanRun > maxNanRun) {
                    maxNanRun = currentNanRun;
                }
                continue;
            }
            currentNanRun = 0; // Reset the current NaN run
            validCount++;
            if (firstValidIndex == -1) {
                firstValidIndex = i;
            }
            if (lastValidIndex == -1 || i > lastValidIndex) {
                lastValidIndex = i;
            }
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }

        double validPercentage = (validCount * 100.0) / total;
        double nanPercentage = (nanCount * 100.0) / total;


        System.out.println("---- Decode Stats ----");
        System.out.println("Total values: " + total);
        System.out.println("Valid values: " + validCount);
        System.out.println("NaN values: " + nanCount);
        System.out.println("Longest NaN run: " + maxNanRun);
        System.out.println("Valid percentage: " + String.format("%.2f%%%n", validPercentage));
        System.out.println("NaN percentage: " + String.format("%.2f%%%n", nanPercentage));
        System.out.println("First valid index number: " + (firstValidIndex != -1 ? firstValidIndex : "N/A"));
        System.out.println("First valid index value: " + (firstValidIndex != -1 ? values[firstValidIndex] : "N/A"));
        System.out.println("Last valid index number: " + (lastValidIndex != -1 ? lastValidIndex : "N/A"));
        System.out.println("Last valid index value: " + (lastValidIndex != -1 ? values[lastValidIndex] : "N/A"));
        System.out.println("Valid Span: " + (lastValidIndex - firstValidIndex + 1) + " values");
        System.out.println("Valid Span Length: " + (lastValidIndex - firstValidIndex + 1) * 4 + " bytes");

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
