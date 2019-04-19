package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.TwoDimensionArraySearchUtils.Companion.findLargestNumberInArrayRange
import com.example.coroutine.util.TwoDimensionArraySearchUtils.Companion.findLargestNumberInResultArray
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class ParallelProblemsKotlinMultiThreadImpl : ParallelProblems {

    private val delayMillis: Long = 1000
    private val numberOfThreads = 4

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {

        val largestNumbersInRangeArray = IntArray(numberOfThreads) { Int.MIN_VALUE } //Int.MIN_SIZE?

        var exitedThreads = 0
        val scanRangeSize = array.size / numberOfThreads
        for (i in 0 until numberOfThreads) {
            thread {
                largestNumbersInRangeArray[i] = findLargestNumberInArrayRange(i * scanRangeSize, scanRangeSize, array)
                exitedThreads += 1
            }
        }

        while (exitedThreads != numberOfThreads) {
            sleep(delayMillis)
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray)
    }

}