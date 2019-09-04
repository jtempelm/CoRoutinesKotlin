package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.Constants.MAX_HASH_LENGTH
import com.example.coroutine.util.PreHashSearchRangeUtils
import com.example.coroutine.util.findLargestNumberInArrayRange
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.collections.ArrayList

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
                    .mapToInt { getUninterrupted(it) }
                    .max()
                    .asInt
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(ex)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

    }

    private fun getUninterrupted(f: Future<Int>): Int {
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
        val totalNumberOfPossibilities = Math.pow(symbolSet.size.toDouble(), MAX_HASH_LENGTH.toDouble()).toInt()
        val scanRangeSize = totalNumberOfPossibilities / this.numberOfThreads

        val sortedSymbolSet = symbolSet.clone()
        Arrays.sort(sortedSymbolSet) //our comparison operation later depends upon a sorted sequence of characters when compared to int values

        val tasks = ArrayList<Callable<String>>(this.numberOfThreads)
        for (i in 0 until this.numberOfThreads) {
            tasks.add(PreHashSearchRange(i * scanRangeSize, scanRangeSize, hash, sortedSymbolSet))
        }

        try {
            val reversedHashResultsList = this.executor.invokeAll(tasks)
            for (reversedHashStringResult in reversedHashResultsList) {
                val result = reversedHashStringResult.get()
                if (!result.isEmpty()) {
                    return result
                }
            }
            return ""
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(ex)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    private inner class PreHashSearchRange internal constructor(private val startOfRange: Int, private val scanRangeSize: Int, private val hash: String, private val symbolSet: CharArray) : Callable<String> {

        override fun call(): String {
            return PreHashSearchRangeUtils.findPreHashValueInRangeFromHash(startOfRange, scanRangeSize, hash, symbolSet)
        }
    }

}