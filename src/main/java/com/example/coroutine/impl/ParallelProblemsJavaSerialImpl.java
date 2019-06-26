package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.coroutine.util.PrimeFactorizationSearchUtilsJava.findPrimeFactorsInRange;


public class ParallelProblemsJavaSerialImpl implements ParallelProblems {

    @Override
    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(0, array.length, array);
        // return TwoDimensionArraySearchUtilsKotlinKt.findLargestNumberInArrayRange(0, array.length, array); //You can call the kotlin implementation directly from Java
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long primeProduct) {
        return findPrimeFactorsInRange(0L, primeProduct);
    }

}