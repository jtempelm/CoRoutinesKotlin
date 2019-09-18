package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.Constants
import com.example.coroutine.util.PreHashSearchRangeUtils
import com.example.coroutine.util.findLargestNumberInArrayRange
import com.example.coroutine.util.findLargestNumberInResultArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

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

    override fun findPrimeFactors(primeProduct: Long): List<Long> {
        TODO("not implemented")
    }

    override fun findPreHashValueFromHash(hash: String, symbolSet: CharArray): String {
        val totalNumberOfPossibilities = Math.pow(symbolSet.size.toDouble(), Constants.MAX_HASH_LENGTH.toDouble()).toInt()
        val scanRangeSize = totalNumberOfPossibilities / this.numberOfCoRoutines
        var preHashValue = ""

        val sortedSymbolSet = symbolSet.clone()
        Arrays.sort(sortedSymbolSet) //our comparison operation later depends upon a sorted sequence of characters when compared to int values

        runBlocking {
            val job = GlobalScope.launch {
                for (i in 0 until numberOfCoRoutines) {
                    launch {
                        val hashGuess = PreHashSearchRangeUtils.findPreHashValueInRangeFromHash(i * scanRangeSize, scanRangeSize, hash, sortedSymbolSet)
                        if (hashGuess != "") {
                            preHashValue = hashGuess
                        }
                    }
                }
            }

            job.join()
        }

        return preHashValue
    }
}