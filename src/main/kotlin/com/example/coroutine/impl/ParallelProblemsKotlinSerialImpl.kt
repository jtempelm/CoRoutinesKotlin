package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems

class ParallelProblemsKotlinSerialImpl : ParallelProblems {
    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        var largestNumber = 0
        for (x in array.indices) {
            for (y in array.indices) {
                if (largestNumber < array[x][y]) {
                    largestNumber = array[x][y]
                }
            }
        }

        return largestNumber
    }

}