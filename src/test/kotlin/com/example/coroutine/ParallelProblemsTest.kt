package com.example.coroutine

import com.example.coroutine.impl.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParallelProblemsTest {

    private var largestNumberGenerated = Int.MIN_VALUE

    companion object {
        private const val ARRAY_SIZE = 30_000
        private const val NUMBER_OF_THREADS = 2
        private const val NUMBER_OF_COROUTINES = 2
    }

    @Test
    fun findTheLargestNumberInA2dArray() {
        val twoDNumberArray = generate2DArray(ARRAY_SIZE)
        // print2DArray(array) //DEBUG, don't run on huge array sizes

        var startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinSerialImpl())
        var endTime = System.currentTimeMillis()
        System.out.println("Serial Kotlin Implementation took ${endTime - startTime}ms")

        startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinMultiThreadImpl(numberOfThreads = NUMBER_OF_THREADS))
        endTime = System.currentTimeMillis()
        System.out.println("MultiThread Kotlin Implementation took ${endTime - startTime}ms with $NUMBER_OF_THREADS threads")

        startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsKotlinCoRoutineImpl(numberOfCoRoutines = NUMBER_OF_COROUTINES))
        endTime = System.currentTimeMillis()
        System.out.println("CoRoutine Kotlin Implementation took ${endTime - startTime}ms with $NUMBER_OF_COROUTINES coroutines")

        startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaSerialImpl())
        endTime = System.currentTimeMillis()
        System.out.println("Serial Java Implementation took ${endTime - startTime}ms")

        startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaMultiThreadingImpl(NUMBER_OF_THREADS))
        endTime = System.currentTimeMillis()
        System.out.println("MultiThread Java Implementation took ${endTime - startTime}ms with $NUMBER_OF_THREADS threads")

        startTime = System.currentTimeMillis()
        findLargestNumberInArrayWithImplementation(twoDNumberArray, ParallelProblemsJavaCoRoutineImpl(NUMBER_OF_COROUTINES))
        endTime = System.currentTimeMillis()
        System.out.println("CoRoutine Java Implementation took ${endTime - startTime}ms with $NUMBER_OF_COROUTINES coroutines")
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