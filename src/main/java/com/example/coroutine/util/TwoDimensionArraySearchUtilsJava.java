package com.example.coroutine.util;

public class TwoDimensionArraySearchUtilsJava {

    public static int findLargestNumberInArrayRange(int startOfRange, int scanRangeSize, int[][] array) {
        int largestNumber = Integer.MIN_VALUE;
        for (int x = startOfRange; x < (startOfRange + scanRangeSize); x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (largestNumber < array[x][y]) {
                    largestNumber = array[x][y];
                }
            }
        }

        return largestNumber;
    }

    public static int findLargestNumberInResultArray(int[] largestNumbersInRange) {
        int largestNumber = Integer.MIN_VALUE;
        for (int i = 0; i< largestNumbersInRange.length; i++) {
            if (largestNumber < largestNumbersInRange[i]) {
                largestNumber = largestNumbersInRange[i];
            }
        }

        return largestNumber;
    }
}