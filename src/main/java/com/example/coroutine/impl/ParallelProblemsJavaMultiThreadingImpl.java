package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.PreHashSearchRangeUtils;
import com.example.coroutine.util.PrimeFactorizationSearchUtilsJava;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.example.coroutine.util.Constants.MAX_HASH_LENGTH;

public class ParallelProblemsJavaMultiThreadingImpl implements ParallelProblems {

    private final java.util.concurrent.ExecutorService executor;
    private final int numberOfThreads;

    public ParallelProblemsJavaMultiThreadingImpl(final int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        final int scanRangeSize = array.length / this.numberOfThreads;

        final List<Callable<Integer>> tasks = new ArrayList<>(this.numberOfThreads);
        for (int i = 0; i < this.numberOfThreads; i++) {
            tasks.add(new SearchArrayRange(i * scanRangeSize, scanRangeSize, array));
        }

        try {
            return this.executor.invokeAll(tasks)
                .stream()
                .mapToInt(ParallelProblemsJavaMultiThreadingImpl::getWithoutInterruption)
                .max()
                .getAsInt();
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Integer getWithoutInterruption(final Future<Integer> future) {
        while (true) {
            try {
                return future.get();
            } catch (final InterruptedException ignored) {
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final class SearchArrayRange implements Callable<Integer> {

        private final int startOfRange;
        private final int scanRangeSize;
        private final int[][] array;

        SearchArrayRange(final int startOfRange, final int scanRangeSize, final int[][] array) {
            this.startOfRange = startOfRange;
            this.scanRangeSize = scanRangeSize;
            this.array = array;
        }

        public Integer call() {
            return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(startOfRange, scanRangeSize, array);
        }
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long primePairProduct) {
        final long scanRangeSize = primePairProduct / this.numberOfThreads;

        final List<Callable<List<Long>>> tasks = new ArrayList<>(this.numberOfThreads);
        for (int i = 0; i < this.numberOfThreads; i++) {
            tasks.add(new PrimeFactorSearchRange(i * scanRangeSize, scanRangeSize));
        }

        try {
            return this.executor.invokeAll(tasks)
                .stream()
                .map(ParallelProblemsJavaMultiThreadingImpl::getListWithoutInterruption)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<Long> getListWithoutInterruption(final Future<List<Long>> listFuture) {
        while (true) {
            try {
                return listFuture.get();
            } catch (final InterruptedException ignored) {
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final class PrimeFactorSearchRange implements Callable<List<Long>> {

        private final long startOfRange;
        private final long scanRangeSize;

        PrimeFactorSearchRange(final long startOfRange, final long scanRangeSize) {
            this.startOfRange = startOfRange;
            this.scanRangeSize = scanRangeSize;
        }

        public List<Long> call() {
            return PrimeFactorizationSearchUtilsJava.findPrimeFactorsInRange(startOfRange, startOfRange + scanRangeSize);
        }
    }

    @NotNull
    @Override
    public String findPreHashValueFromHash(@NotNull final String hash, @NotNull final char[] symbolSet) {
        final int totalNumberOfPossibilities = (int)Math.pow(symbolSet.length, MAX_HASH_LENGTH);
        final int scanRangeSize = totalNumberOfPossibilities / this.numberOfThreads;

        final char[] sortedSymbolSet = symbolSet.clone();
        Arrays.sort(sortedSymbolSet); //our comparison operation later depends upon a sorted sequence of characters when compared to int values

        final List<Callable<String>> tasks = new ArrayList<>(this.numberOfThreads);
        for (int i = 0; i < this.numberOfThreads; i++) {
            tasks.add(new PreHashSearchRange(i * scanRangeSize, scanRangeSize, hash, sortedSymbolSet ));
        }

        try {
            final List<Future<String>> reversedHashResultsList = this.executor.invokeAll(tasks);
            for (final Future<String> reversedHashStringResult : reversedHashResultsList) {
                final String result = reversedHashStringResult.get();
                if (!result.isEmpty()) {
                    return result;
                }
            }
            return "";
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getStringWithoutInterruption(final Future<String> future) {
        while (true) {
            try {
                return future.get();
            } catch (final InterruptedException ignored) {
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final class PreHashSearchRange implements Callable<String> {

        private final int startOfRange;
        private final int scanRangeSize;
        private final String hash;
        private final char[] symbolSet;

        PreHashSearchRange(final int startOfRange, final int scanRangeSize, final String hash, final char[] symbolSet) {
            this.startOfRange = startOfRange;
            this.scanRangeSize = scanRangeSize;
            this.hash = hash;
            this.symbolSet = symbolSet;
        }

        public String call() {
            return PreHashSearchRangeUtils.findPreHashValueInRangeFromHash(startOfRange, scanRangeSize, hash, symbolSet);
        }
    }

}
