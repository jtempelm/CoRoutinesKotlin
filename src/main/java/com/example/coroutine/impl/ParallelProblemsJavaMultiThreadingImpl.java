package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.PrimeFactorizationSearchUtilsJava;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ParallelProblemsJavaMultiThreadingImpl implements ParallelProblems {
    private final java.util.concurrent.ExecutorService executor;
    private final int numberOfThreads;

    public ParallelProblemsJavaMultiThreadingImpl(final int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

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
        return null;
    }

}
