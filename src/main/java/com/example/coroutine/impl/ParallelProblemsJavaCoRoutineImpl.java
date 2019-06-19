package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import de.esoco.coroutine.CoroutineScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.coroutine.util.TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange;
import static com.example.coroutine.util.TwoDimensionArraySearchUtilsJava.findLargestNumberInResultArray;
import static java.lang.Thread.sleep;

public class ParallelProblemsJavaCoRoutineImpl implements ParallelProblems {

    private static int DELAY_MILLIS = 1000;
    private int NUMBER_OF_COROUTINES;

    public ParallelProblemsJavaCoRoutineImpl(int numberOfCoRoutines) {
        this.NUMBER_OF_COROUTINES = numberOfCoRoutines;
    }

    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        int[] largestNumbersInRangeArray = new int[NUMBER_OF_COROUTINES];
        Arrays.fill(largestNumbersInRangeArray, Integer.MIN_VALUE);

        AtomicInteger exitedCoRoutines = new AtomicInteger(0); //threadsafe atomic int?
        int scanRangeSize = array.length / NUMBER_OF_COROUTINES;
        for (int i = 0; i < NUMBER_OF_COROUTINES; i++) {
            final int coRoutineIndex = i;
            final int startOfRange = i * scanRangeSize; //variable used in lambda expression but be final or effectively final
            CoroutineScope.launch(scope -> {
                largestNumbersInRangeArray[coRoutineIndex] = findLargestNumberInArrayRange(startOfRange, scanRangeSize, array);
                exitedCoRoutines.incrementAndGet();
            });
        }

        while (exitedCoRoutines.intValue() < NUMBER_OF_COROUTINES) {
            try {
                sleep(DELAY_MILLIS);
            } catch (InterruptedException ignored) {
            }
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray);
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long n) {
        return new ArrayList<Long>();
    }
}