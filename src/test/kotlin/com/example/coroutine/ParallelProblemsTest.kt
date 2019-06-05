package com.example.coroutine

import com.example.coroutine.impl.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

private const val ARRAY_SIZE = 100 //benchmark ran at 30_000 with: VM Options: -ea -Xms2G -Xmx5G
private const val NUMBER_OF_THREADS = 2
private const val NUMBER_OF_COROUTINES = 2
private const val TEST_ITERATIONS = 10

private const val PRIME_PRODUCT_1 = 6 //the simplest possible example
private const val PRIME_PRODUCT_1_FACTOR_SMALL = 2
private const val PRIME_PRODUCT_1_FACTOR_LARGE = 3

private const val PRIME_PRODUCT_2 = 901
private const val PRIME_PRODUCT_2_FACTOR_SMALL = 17
private const val PRIME_PRODUCT_2_FACTOR_LARGE = 53

private const val PRIME_PRODUCT_3 = 82885603
private const val PRIME_PRODUCT_3_FACTOR_SMALL = 8311
private const val PRIME_PRODUCT_3_FACTOR_LARGE = 9973

private const val PRIME_PRODUCT_4 = 6569317
private const val PRIME_PRODUCT_4_FACTOR_SMALL = 2113
private const val PRIME_PRODUCT_4_FACTOR_LARGE = 3109

private const val PRIME_PRODUCT_5 = 333797
private const val PRIME_PRODUCT_5_FACTOR_SMALL = 541
private const val PRIME_PRODUCT_5_FACTOR_LARGE = 617

class ParallelProblemsTest {
    private val twoDNumberArray = generate2DArray(ARRAY_SIZE)
    private val largestNumberGenerated = largestNumber(twoDNumberArray)

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            // print2DArray(array) //DEBUG, don't run on huge array sizes
        }
    }


    @Test
    fun findTheLargestNumberInA2dArray_kotlinSerial() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Kotlin Serial Implementation findTheLargestNumberInA2dArray  - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinSerialImpl())
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Kotlin Multi Thread Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinMultiThreadImpl(numberOfThreads = NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Kotlin CoRoutine Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinCoRoutineImpl(numberOfCoRoutines = NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaSerial() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Java Serial Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaSerialImpl())
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Java Multi Thread Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaMultiThreadingImpl(NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Java CoRoutine Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaCoRoutineImpl(NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            System.out.println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findPrimeFactorPair_javaSerial() {
        for (i in 1..TEST_ITERATIONS) {
            System.out.print("Java Serial Implementation findPrimeFactorPair - test run ${i} - ")

            findPrimeFactorPairWithImplementation(PRIME_PRODUCT_5, PRIME_PRODUCT_5_FACTOR_SMALL, PRIME_PRODUCT_5_FACTOR_LARGE, ParallelProblemsJavaSerialImpl())
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

        val startTime = System.currentTimeMillis()
        val largestNumber = parallelProblems.findLargestNumberInAnArray(twoDNumberArray)
        val endTime = System.currentTimeMillis()
        System.out.println("${endTime - startTime}ms")

        assertEquals(largestNumberGenerated, largestNumber)
    }

    private fun findPrimeFactorPairWithImplementation(primePairProduct: Int, primeFactorSmall: Int, primeFactorLarge: Int, parallelProblems: ParallelProblems) {
        val startTime = System.currentTimeMillis()
        val primePair = parallelProblems.findPrimeFactors(primePairProduct)
        val endTime = System.currentTimeMillis()
        System.out.println("${endTime - startTime}ms")

        assertEquals(primePair.size, 2)
        assertEquals(primePair[0], primeFactorSmall)
        assertEquals(primePair[1], primeFactorLarge)
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
