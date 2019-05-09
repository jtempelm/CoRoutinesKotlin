package com.example.coroutine.util

internal fun findLargestNumberInArrayRange(startOfRange: Int, scanRangeSize: Int, array: Array<IntArray>): Int {
    var largestNumber = Int.MIN_VALUE
    for (x in startOfRange until (startOfRange + scanRangeSize)) {
        for (y in array[x].indices) {
            if (largestNumber < array[x][y]) {
                largestNumber = array[x][y]
            }
        }
    }

    return largestNumber
}

internal fun findLargestNumberInResultArray(largestNumbersInRange: IntArray): Int {
    var largestNumber = Int.MIN_VALUE
    for (i in largestNumbersInRange.indices) {
        if (largestNumber < largestNumbersInRange[i]) {
            largestNumber = largestNumbersInRange[i]
        }
    }

    return largestNumber
}