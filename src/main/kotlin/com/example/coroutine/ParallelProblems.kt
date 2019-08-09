package com.example.coroutine

interface ParallelProblems {

    fun findLargestNumberInAnArray(array: Array<IntArray>): Int

    fun findPrimeFactors(primeProduct: Long): List<Long>

    fun findPreHashValueFromHash(hash: String, symbolSet: CharArray): String
}