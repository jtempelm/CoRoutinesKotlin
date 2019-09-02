package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.Constants.MAX_HASH_LENGTH
import com.example.coroutine.util.findLargestNumberInArrayRange
import com.example.coroutine.util.findPreHashValueInRangeFromHash
import java.util.*

class ParallelProblemsKotlinSerialImpl : ParallelProblems {
    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        return findLargestNumberInArrayRange(startOfRange = 0, scanRangeSize = array.size, array = array)
    }

    override fun findPrimeFactors(primeProduct: Long): List<Long> {
        val primeFactors = mutableListOf<Long>()

        if (primeProduct < 2L) {
            return emptyList()
        }

        var remainder = primeProduct
        while (remainder % 2L == 0L) {
            if (!primeFactors.contains(2L)) {
                primeFactors.add(2L)
            }
            remainder /= 2L
        }

        var i = 3L
        while (i <= remainder / i) {
            while (remainder % i == 0L) {
                if (!primeFactors.contains(i)) {
                    primeFactors.add(i)
                }
                remainder /= i
            }

            i += 2L
        }

        if (remainder > 1) {
            primeFactors.add(remainder)
        }

        return primeFactors
    }

    override fun findPreHashValueFromHash(hash: String, symbolSet: CharArray): String {

        val sortedSymbolSet = symbolSet.clone()
        Arrays.sort(sortedSymbolSet) //our comparison operation later depends upon a sorted sequence of characters when compared to int values

        val totalNumberOfPossibilities = Math.pow(symbolSet.size.toDouble(), MAX_HASH_LENGTH.toDouble()).toInt()

        return findPreHashValueInRangeFromHash(0, totalNumberOfPossibilities, hash, sortedSymbolSet)
    }
}