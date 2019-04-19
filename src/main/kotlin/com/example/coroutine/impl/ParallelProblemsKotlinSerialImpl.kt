package com.example.coroutine.impl

import com.example.coroutine.ParallelProblems
import com.example.coroutine.util.TwoDimensionArraySearchUtils.Companion.findLargestNumberInArrayRange

class ParallelProblemsKotlinSerialImpl : ParallelProblems {
    override fun findLargestNumberInAnArray(array: Array<IntArray>): Int {
        return findLargestNumberInArrayRange(startOfRange = 0, scanRangeSize = array.size, array = array)
    }

}