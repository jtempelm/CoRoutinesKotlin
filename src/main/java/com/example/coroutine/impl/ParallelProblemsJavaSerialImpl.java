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
    public List<Integer> findPrimeFactors(final int primeProduct) {
        final List<Integer> primeNumbersLessThanN = findPrimeNumbersLessThanPrimeProduct(primeProduct);

        return findPrimeFactorPair(primeProduct, primeNumbersLessThanN);
    }

    private List<Integer> findPrimeNumbersLessThanPrimeProduct(final int primeProduct) {
        final List<Integer> primesInRange = new ArrayList<Integer>();
        for (int i = 1; i < primeProduct; i++) {
            if (isPrime(i)) {
                primesInRange.add(i);
            }
        }

        return primesInRange;
    }

    private boolean isPrime(final int number) {
        if (number <= 1) {
            return false;
        }

        for (int j = 2; j < number; j++) {
            if (number % j == 0) {
                return false;
            }
        }

        return true;
    }

    private List<Integer> findPrimeFactorPair(final Integer n, final List<Integer> primeNumbersLessThanN) {
        final List<Integer> primeFactorPair = new ArrayList<Integer>();

        for (final Integer p : primeNumbersLessThanN) {
            for (final Integer q : primeNumbersLessThanN) {
                if (p * q == n) {
                    primeFactorPair.add(p);
                    primeFactorPair.add(q);

                    return primeFactorPair;
                }
            }
        }

        return primeFactorPair;
    }

}