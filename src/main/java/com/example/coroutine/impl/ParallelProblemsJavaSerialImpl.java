package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ParallelProblemsJavaSerialImpl implements ParallelProblems {

    @Override
    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(0, array.length, array);
        // return TwoDimensionArraySearchUtilsKotlinKt.findLargestNumberInArrayRange(0, array.length, array); //You can call the kotlin implementation directly from Java
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long primeProduct) {
        final List<Long> primeFactors = new ArrayList<Long>();

        if (primeProduct < 2) {
            return primeFactors;
        }

        Long remainder = primeProduct;
        while (remainder % 2 == 0L) {
            if (!primeFactors.contains(2L)) {
                primeFactors.add(2L);
            }
            remainder /= 2L;
        }

        Long i = 3L;
        while (i <= remainder / i) {
            while (remainder % i == 0) {
                if (!primeFactors.contains(i)) {
                    primeFactors.add(i);
                }
                remainder /= i;
            }

            i += 2;
        }

        if (remainder > 1) {
            primeFactors.add(remainder);
        }

        return primeFactors;
    }

}