package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.TwoDimensionArraySearchUtils.Companion.findLargestNumberInArrayRange
import com.example.coroutine.util.TwoDimensionArraySearchUtils.Companion.findLargestNumberInResultArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ParallelProblemsKotlinCoRoutineImpl : ParallelProblems {

    private val delayMillis: Long = 1000
    private val numberOfCoRoutines = 4
    private val largestNumbersInRangeArray = IntArray(numberOfCoRoutines) { Int.MIN_VALUE } //Int.MIN_SIZE?
    private var exitedCoRoutines = 0

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {

        val scanRangeSize = array.size / numberOfCoRoutines
        for (i in 0 until numberOfCoRoutines) {
            GlobalScope.launch {
                largestNumbersInRangeArray[i] = findLargestNumberInArrayRange(i * scanRangeSize, scanRangeSize, array)
                exitedCoRoutines += 1
            }
        }

        while (exitedCoRoutines != numberOfCoRoutines) {
            Thread.sleep(delayMillis)
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray)
    }
}