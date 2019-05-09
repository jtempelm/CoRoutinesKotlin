package com.example.coroutine.util;

public class TwoDimensionArraySearchUtilsJava {

    public static int findLargestNumberInArrayRange(int startOfRange, int scanRangeSize, int[][] array) {
        int largestNumber = Integer.MIN_VALUE;

        final java.util.List<int[]> xrange = new java.util.ArrayList<>(startOfRange + scanRangeSize);
        for (int x = startOfRange, i = 0; x < (startOfRange + scanRangeSize); x++, i++) {
            xrange.add(array[x]);
        }

        for (int[] yrange : xrange) {
            for (int i : yrange) {
                if (largestNumber < i) {
                    largestNumber = i;
                }
            }
        }

        return largestNumber;
    }

    public static int findLargestNumberInResultArray(int[] largestNumbersInRange) {
        int largestNumber = Integer.MIN_VALUE;

        for (int i = 0; i < largestNumbersInRange.length; i++) {
            if (largestNumber < largestNumbersInRange[i]) {
                largestNumber = largestNumbersInRange[i];
            }
        }

        return largestNumber;
    }
}
