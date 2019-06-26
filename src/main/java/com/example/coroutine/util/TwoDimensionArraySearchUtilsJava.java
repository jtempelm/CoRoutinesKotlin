package com.example.coroutine.util;

public class TwoDimensionArraySearchUtilsJava {

    public static int findLargestNumberInArrayRange(final int startOfRange, final int scanRangeSize, final int[][] array) {
        int largestNumber = Integer.MIN_VALUE;

        final java.util.List<int[]> xrange = new java.util.ArrayList<>(startOfRange + scanRangeSize);
        for (int x = startOfRange; x < (startOfRange + scanRangeSize); x++) {
            xrange.add(array[x]);
        }

        for (final int[] yRange : xrange) {
            for (final int i : yRange) {
                if (largestNumber < i) {
                    largestNumber = i;
                }
            }
        }

        return largestNumber;
    }

    public static int findLargestNumberInResultArray(final int[] largestNumbersInRange) {
        int largestNumber = Integer.MIN_VALUE;

        for (final int value : largestNumbersInRange) {
            if (largestNumber < value) {
                largestNumber = value;
            }
        }

        return largestNumber;
    }
}
