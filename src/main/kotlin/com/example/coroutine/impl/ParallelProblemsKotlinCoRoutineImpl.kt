package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.findLargestNumberInArrayRange
import com.example.coroutine.util.findLargestNumberInResultArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

private const val delayMillis: Long = 1000

class ParallelProblemsKotlinCoRoutineImpl(private val numberOfCoRoutines: Int) : ParallelProblems {
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