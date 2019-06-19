package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.findLargestNumberInArrayRange

class ParallelProblemsKotlinSerialImpl : ParallelProblems {
    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        return findLargestNumberInArrayRange(startOfRange = 0, scanRangeSize = array.size, array = array)
    }

    override fun findPrimeFactors(primeProduct: Long): List<Long> {
        if (primeProduct < 2L) return emptyList()

        val primeFactors = mutableListOf<Long>()
        var remainder = primeProduct
        while (remainder % 2L == 0L) {
            primeFactors.add(2L)
            remainder /= 2L
        }

        var i = 3L
        while (i <= remainder / i) {
            while (remainder % i == 0L) {
                primeFactors.add(i)
                remainder /= i
            }

            i += 2L
        }

        if (remainder > 1) {
            primeFactors.add(remainder)
        }

        return primeFactors
    }
}