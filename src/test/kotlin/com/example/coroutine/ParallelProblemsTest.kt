package com.example.coroutine

import java.util.Arrays
import com.example.coroutine.impl.*
import com.example.coroutine.impl.ParallelProblemsJavaCoRoutineImpl
import com.example.coroutine.impl.ParallelProblemsJavaMultiThreadingImpl
import com.example.coroutine.impl.ParallelProblemsJavaSerialImpl
import com.example.coroutine.impl.ParallelProblemsKotlinCoRoutineImpl
import com.example.coroutine.impl.ParallelProblemsKotlinMultiThreadImpl
import com.example.coroutine.impl.ParallelProblemsKotlinSerialImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import kotlin.test.assertEquals

private const val ARRAY_SIZE = 30_000
private const val NUMBER_OF_THREADS = 2
private const val NUMBER_OF_COROUTINES = 2

class ParallelProblemsTest {
    private val twoDNumberArray = generate2DArray(ARRAY_SIZE)
    private val largestNumberGenerated = largestNumber(twoDNumberArray)

    companion object {
        private const val ARRAY_SIZE = 30_000
        private const val NUMBER_OF_THREADS = 8
        private const val NUMBER_OF_COROUTINES = 8

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            // print2DArray(array) //DEBUG, don't run on huge array sizes
        }
    }


    @Test
    fun findTheLargestNumberInA2dArray_kotlinSerial() {
        for (i in 1..10) {
            System.out.print("Serial Kotlin Implementation  - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinSerialImpl())
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinMultiThread() {
        for (i in 1..10) {
            System.out.print("Kotlin Multi Thread Implementation - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinMultiThreadImpl(numberOfThreads = NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinCoRoutine() {
        for (i in 1..10) {
            System.out.print("Serial CoRoutine Implementation - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinCoRoutineImpl(numberOfCoRoutines = NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaSerial() {
        for (i in 1..10) {
            System.out.print("Java Serial Implementation - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaSerialImpl())
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaMultiThread() {
        for (i in 1..10) {
            System.out.print("Java Multi Thread Implementation - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaMultiThreadingImpl(NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaCoRoutine() {
        for (i in 1..10) {
            System.out.print("Java CoRoutine Implementation - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaCoRoutineImpl(NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }


    private fun generate2DArray(size: Int): Array<IntArray> {
        val twoDNumberArray = Array(size) { IntArray(size) }

        for (x in twoDNumberArray.indices) {
            for (y in twoDNumberArray.indices) {
                twoDNumberArray[x][y] = (Int.MIN_VALUE..Int.MAX_VALUE).random()
            }
        }

        return twoDNumberArray
    }

    private fun largestNumber(twoDNumberArray: Array<IntArray>): Int {
        var largestNumberGenerated = Int.MIN_VALUE

        for (x in twoDNumberArray.indices) {
            for (y in twoDNumberArray.indices) {
                if (largestNumberGenerated < twoDNumberArray[x][y]) {
                    largestNumberGenerated = twoDNumberArray[x][y]
                }
            }
        }

        System.out.println("Largest number generated was $largestNumberGenerated")

        return largestNumberGenerated
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
