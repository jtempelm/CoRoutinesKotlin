package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.findLargestNumberInArrayRange

class ParallelProblemsKotlinSerialImpl : ParallelProblems {
    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        return findLargestNumberInArrayRange(startOfRange = 0, scanRangeSize = array.size, array = array)
    }

    override fun findPrimeFactors(primeProduct: Long): List<Long> { //TODO make this a util for multithread impl
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

        var i = 3L //TODO range assignment ternary
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}