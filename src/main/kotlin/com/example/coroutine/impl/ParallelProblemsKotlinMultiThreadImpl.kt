package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.findLargestNumberInArrayRange
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ParallelProblemsKotlinMultiThreadImpl(private val numberOfThreads: Int) : ParallelProblems {

    private val executor = Executors.newFixedThreadPool(numberOfThreads)

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        val scanRangeSize = array.size / this.numberOfThreads

        val tasks = ArrayList<Callable<Int>>(this.numberOfThreads)
        for (i in 0 until this.numberOfThreads) {
            tasks.add(SearchArrayRange(i * scanRangeSize, scanRangeSize, array))
        }

        try {
            return this.executor.invokeAll<Int>(tasks)
                    .stream()
                    .mapToInt { getUninterruptibly(it) }
                    .max()
                    .asInt
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(ex)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

    }

    private fun getUninterruptibly(f: Future<Int>): Int {
        while (true) {
            try {
                return f.get()
            } catch (ex: InterruptedException) {
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }

        }
    }

    private inner class SearchArrayRange(private val startOfRange: Int, private val scanRangeSize: Int, private val array: Array<IntArray>) : Callable<Int> {

        override fun call(): Int {
            return findLargestNumberInArrayRange(startOfRange, scanRangeSize, array)
        }
    }

    override fun findPrimeFactors(n: Long): List<Long> {
        TODO("not implemented")
    }

    override fun findPreHashValueFromHash(hash: String, symbolSet: CharArray): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}