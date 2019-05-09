package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class ParallelProblemsJavaMultiThreadingImpl implements ParallelProblems {
    private final java.util.concurrent.ExecutorService executor;
    private final int numberOfThreads;

    public ParallelProblemsJavaMultiThreadingImpl(final int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        int scanRangeSize = array.length / this.numberOfThreads;

        final List<Callable<Integer>> tasks = new ArrayList<>(this.numberOfThreads);
        for (int i = 0; i < this.numberOfThreads; i++) {
            tasks.add(new SearchArrayRange(i * scanRangeSize, scanRangeSize, array, i));
        }

        try {
            return this.executor.invokeAll(tasks)
                .stream()
                .mapToInt(ParallelProblemsJavaMultiThreadingImpl::getUninteruptably)
                .max()
                .getAsInt();
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Integer getUninteruptably(final Future<Integer> f) {
        for (;;) {
            try {
                return f.get();
            } catch (final InterruptedException ex) {
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final class SearchArrayRange implements Callable<Integer> {

        private final int startOfRange;
        private final int scanRangeSize;
        private final int[][] array;
        private final int threadIndex;

        public SearchArrayRange(int startOfRange, int scanRangeSize, int[][] array, int threadIndex) {
            this.startOfRange = startOfRange;
            this.scanRangeSize = scanRangeSize;
            this.array = array;
            this.threadIndex = threadIndex;
        }

        public Integer call() {
            return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(startOfRange, scanRangeSize, array);
        }
    }
}
