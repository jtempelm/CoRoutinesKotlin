package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.findLargestNumberInArrayRange
import com.example.coroutine.util.findLargestNumberInResultArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val delayMillis: Long = 1000

class ParallelProblemsKotlinCoRoutineImpl(private val numberOfCoRoutines: Int) : ParallelProblems {
    private val largestNumbersInRangeArray = IntArray(numberOfCoRoutines) { Int.MIN_VALUE } //Int.MIN_SIZE?

    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        runBlocking {
            val job = GlobalScope.launch {
                val scanRangeSize = array.size / numberOfCoRoutines
                for (i in 0 until numberOfCoRoutines) {
                    launch {
                        largestNumbersInRangeArray[i] = findLargestNumberInArrayRange(i * scanRangeSize,
                                scanRangeSize,
                                array)
                    }
                }
            }

            job.join()
        }

        return findLargestNumberInResultArray(largestNumbersInRangeArray)
    }

    override fun findPrimeFactors(n: Long): List<Long> {
        TODO("not implemented")
    }

    override fun findPreHashValueFromHash(hash: String, symbolSet: CharArray): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}