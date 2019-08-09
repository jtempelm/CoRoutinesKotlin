package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import de.esoco.coroutine.CoroutineScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.coroutine.util.TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange;
import static com.example.coroutine.util.TwoDimensionArraySearchUtilsJava.findLargestNumberInResultArray;

//based on https://medium.com/@esocogmbh/coroutines-in-pure-java-65661a379c85
public class ParallelProblemsJavaCoRoutineImpl implements ParallelProblems {

    private final int NUMBER_OF_COROUTINES;

    public ParallelProblemsJavaCoRoutineImpl(final int numberOfCoRoutines) {
        this.NUMBER_OF_COROUTINES = numberOfCoRoutines;
    }

    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        final int[] largestNumbersInRangeArray = new int[NUMBER_OF_COROUTINES];
        Arrays.fill(largestNumbersInRangeArray, Integer.MIN_VALUE);

        final int scanRangeSize = array.length / NUMBER_OF_COROUTINES;
        for (int i = 0; i < NUMBER_OF_COROUTINES; i++) {
            final int coRoutineIndex = i;
            final int startOfRange = i * scanRangeSize; //variable used in lambda expression but be final or effectively final
            CoroutineScope.launch(scope -> {
                largestNumbersInRangeArray[coRoutineIndex] = findLargestNumberInArrayRange(startOfRange, scanRangeSize, array);
            });
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray);
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long n) {
        return new ArrayList<Long>();
    }

    @NotNull
    @Override
    public String findPreHashValueFromHash(@NotNull final String hash, @NotNull final char[] symbolSet) {
        return null;
    }
}