package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.TwoDimensionArraySearchUtilsKotlin.Companion.findLargestNumberInArrayRange
import com.example.coroutine.util.TwoDimensionArraySearchUtilsKotlin.Companion.findLargestNumberInResultArray
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class ParallelProblemsKotlinMultiThreadImpl : ParallelProblems {

    private val delayMillis: Long = 1000
    private val numberOfThreads = 4

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {

        val largestNumbersInRangeArray = IntArray(numberOfThreads) { Int.MIN_VALUE } //Int.MIN_SIZE?

        var exitedThreads = AtomicInteger(0)
        val scanRangeSize = array.size / numberOfThreads
        for (i in 0 until numberOfThreads) {
            thread {
                largestNumbersInRangeArray[i] = findLargestNumberInArrayRange(i * scanRangeSize, scanRangeSize, array)
                exitedThreads.incrementAndGet()
            }
        }

        while (exitedThreads.toInt() != numberOfThreads) {
            sleep(delayMillis)
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray)
    }

}