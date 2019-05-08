package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.TwoDimensionArraySearchUtilsKotlin.Companion.findLargestNumberInArrayRange
import com.example.coroutine.util.TwoDimensionArraySearchUtilsKotlin.Companion.findLargestNumberInResultArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class ParallelProblemsKotlinCoRoutineImpl(private val numberOfCoRoutines: Int) : ParallelProblems {

    private val delayMillis: Long = 1000
    private val largestNumbersInRangeArray = IntArray(numberOfCoRoutines) { Int.MIN_VALUE } //Int.MIN_SIZE?
    private var exitedCoRoutines = AtomicInteger(0)

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {

        val scanRangeSize = array.size / numberOfCoRoutines
        for (i in 0 until numberOfCoRoutines) {
            GlobalScope.launch {
                largestNumbersInRangeArray[i] = findLargestNumberInArrayRange(i * scanRangeSize, scanRangeSize, array)
                exitedCoRoutines.incrementAndGet()
            }
        }

        while (exitedCoRoutines.toInt() < numberOfCoRoutines) {
            Thread.sleep(delayMillis)
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray)
    }
}