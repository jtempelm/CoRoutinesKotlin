package com.example.coroutine

import com.example.coroutine.impl.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParallelProblemsTest {

    private var largestNumberGenerated = Int.MIN_VALUE

    companion object {
        private const val ARRAY_SIZE = 20
    }

    @Test
    fun findTheLargestNumberInA2dArray() {
        val twoDNumberArray = generate2DArray(ARRAY_SIZE)
        // print2DArray(array) //DEBUG, don't run on huge array sizes

        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinSerialImpl())
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinMultiThreadImpl())
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinCoRoutineImpl())

        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaSerialImpl())
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaMultiThreadingImpl())
        // findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaCoRoutineImpl())
    }

    private fun generate2DArray(size: Int): Array<IntArray> {
        val twoDNumberArray = Array(size) { IntArray(size) }

        for (x in twoDNumberArray.indices) {
            for (y in twoDNumberArray.indices) {
                twoDNumberArray[x][y] = (Int.MIN_VALUE..Int.MAX_VALUE).random()
                if (largestNumberGenerated < twoDNumberArray[x][y]) {
                    largestNumberGenerated = twoDNumberArray[x][y]
                }
            }
        }

        System.out.println("Largest number generated was $largestNumberGenerated")

        return twoDNumberArray
    }

    private fun findLargestNumberInArrayWithImplementation(twoDNumberArray: Array<IntArray>, parallelProblems: ParallelProblems) {
        val largestNumber = parallelProblems.findLargestNumberInAnArray(twoDNumberArray) //TODO profile these for the times
        assertEquals(largestNumberGenerated, largestNumber)
    }


    private fun print2DArray(array: Array<IntArray>) {
        for (x in array.indices) {
            for (y in array.indices) {
                System.out.print("${array[x][y]} ")
            }
            System.out.println("")
        }
    }


}