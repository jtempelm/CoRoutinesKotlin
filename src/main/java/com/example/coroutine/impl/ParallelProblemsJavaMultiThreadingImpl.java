package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class ParallelProblemsJavaMultiThreadingImpl implements ParallelProblems {

    private static int DELAY_MILLIS = 1000;
    private int NUMBER_OF_THREADS = 4;

    public int findLargestNumberInAnArray(@NotNull final int[][] array) {

        int[] largestNumbersInRangeArray = new int[NUMBER_OF_THREADS];
        Arrays.fill(largestNumbersInRangeArray, Integer.MIN_VALUE);

        AtomicInteger exitedThreads = new AtomicInteger(0); //threadsafe atomic int?
        int scanRangeSize = array.length / NUMBER_OF_THREADS;
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new SearchArrayRange(i * scanRangeSize, scanRangeSize, array, i, largestNumbersInRangeArray, exitedThreads).run();
        }

        while (exitedThreads.intValue() != NUMBER_OF_THREADS) {
            try {
                sleep(DELAY_MILLIS);
            } catch (InterruptedException ignored) {
            }
        }

        return TwoDimensionArraySearchUtilsJava.findLargestNumberInResultArray(largestNumbersInRangeArray);
    }

    private class SearchArrayRange implements Runnable {

        private int startOfRange;
        private int scanRangeSize;
        private int[][] array;
        private int threadIndex;
        private int[] largestNumbersInRangeArray;
        private AtomicInteger exitedThreads;

        public SearchArrayRange(int startOfRange, int scanRangeSize, int[][] array, int threadIndex, int[] largestNumbersInRangeArray, AtomicInteger exitedThreads) {
            this.startOfRange = startOfRange;
            this.scanRangeSize = scanRangeSize;
            this.array = array;
            this.threadIndex = threadIndex;
            this.largestNumbersInRangeArray = largestNumbersInRangeArray;
            this.exitedThreads = exitedThreads;
        }

        public void run() {
            largestNumbersInRangeArray[threadIndex] = TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(startOfRange, scanRangeSize, array);
            exitedThreads.incrementAndGet();
        }
    }
}