package com.example.coroutine

import com.example.coroutine.impl.*
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility.getBytes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.security.MessageDigest
import java.util.*
import kotlin.test.assertEquals


private const val NUMBER_OF_THREADS = 2
private const val NUMBER_OF_COROUTINES = 2
private const val TEST_ITERATIONS = 1

private const val ARRAY_SIZE = 100 //benchmark ran at 30_000 with: VM Options: -ea -Xms2G -Xmx5G

private const val PRIME_PRODUCT_1 = 6L //the simplest possible example
private const val PRIME_PRODUCT_1_FACTOR_SMALL = 2L
private const val PRIME_PRODUCT_1_FACTOR_LARGE = 3L

private const val PRIME_PRODUCT_2 = 43481274954679L
private const val PRIME_PRODUCT_2_FACTOR_SMALL = 6584629L
private const val PRIME_PRODUCT_2_FACTOR_LARGE = 6603451L

private const val PRIME_PRODUCT_3 = 228574383647621387L
private const val PRIME_PRODUCT_3_FACTOR_SMALL = 458476141L
private const val PRIME_PRODUCT_3_FACTOR_LARGE = 498552407L

private const val PRIME_PRODUCT_4 = 1437919224904276069L
private const val PRIME_PRODUCT_4_FACTOR_SMALL = 1058478403L
private const val PRIME_PRODUCT_4_FACTOR_LARGE = 1358477623L

//private const val PRIME_PRODUCT_5 = 12233986365800098933857L //Out of range of long
//private const val PRIME_PRODUCT_5_FACTOR_SMALL = 101058482731L
//private const val PRIME_PRODUCT_5_FACTOR_LARGE = 171058480547L

private const val passwordString1 = "ab12"
private const val passwordString2 = "pass12"
private const val passwordString3 = "password"
private const val passwordString4 = "j0l1yR0D3erRu13sTh3Wav3s!"
private val symbolSet = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+', '{', '}', '[', ']', ';', ':', '`')
private val symbolSetEasy = charArrayOf('a', 'b', 'c', '1', '2', '3')
//String list constant for available symbols, that we will mod wrap around?

//Factorize: http://magma.maths.usyd.edu.au/calc/ Factorization(82885603);
//Prime Check: http://www.math.com/students/calculators/source/prime-number.htm
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
            print("Kotlin Serial Implementation findTheLargestNumberInA2dArray  - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinSerialImpl())
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Multi Thread Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinMultiThreadImpl(numberOfThreads = NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_kotlinCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin CoRoutine Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsKotlinCoRoutineImpl(numberOfCoRoutines = NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaSerial() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaSerialImpl())
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Multi Thread Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaMultiThreadingImpl(NUMBER_OF_THREADS))
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findTheLargestNumberInA2dArray_javaCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java CoRoutine Implementation findTheLargestNumberInA2dArray - test run ${i} - ")

            val data = Arrays.copyOf(twoDNumberArray, ARRAY_SIZE)
            val startTime = System.currentTimeMillis()
            findLargestNumberInArrayWithImplementation(data, ParallelProblemsJavaCoRoutineImpl(NUMBER_OF_COROUTINES))
            val endTime = System.currentTimeMillis()
            println("${endTime - startTime}ms")
        }
    }

    @Test
    fun findPrimeFactorPair_javaSerial() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findPrimeFactorPair - test run ${i} - ")

            findPrimeFactorPairWithImplementation(
                    PRIME_PRODUCT_4,
                    PRIME_PRODUCT_4_FACTOR_SMALL,
                    PRIME_PRODUCT_4_FACTOR_LARGE,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPrimeFactorPair_kotlinSerial() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Serial Implementation findPrimeFactorPair - test run ${i} - ")

            findPrimeFactorPairWithImplementation(
                    PRIME_PRODUCT_4,
                    PRIME_PRODUCT_4_FACTOR_SMALL,
                    PRIME_PRODUCT_4_FACTOR_LARGE,
                    ParallelProblemsKotlinSerialImpl()
            )
        }
    }

    @Test
    fun findPrimeFactorPair_javaMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findPrimeFactorPair - test run ${i} - ")

            findPrimeFactorPairWithImplementation(
                    PRIME_PRODUCT_4,
                    PRIME_PRODUCT_4_FACTOR_SMALL,
                    PRIME_PRODUCT_4_FACTOR_LARGE,
                    ParallelProblemsJavaMultiThreadingImpl(NUMBER_OF_THREADS)
            )
        }
    }

    @Test
    fun findPrimeFactorPair_kotlinMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Serial Implementation findPrimeFactorPair - test run ${i} - ")

            findPrimeFactorPairWithImplementation(
                    PRIME_PRODUCT_4,
                    PRIME_PRODUCT_4_FACTOR_SMALL,
                    PRIME_PRODUCT_4_FACTOR_LARGE,
                    ParallelProblemsKotlinMultiThreadImpl(NUMBER_OF_THREADS)
            )
        }
    }

    @Test
    fun findPreHashValue_javaSerial() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findPreHashValue - test run ${i} - ")
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString3)
                            )
                    )
            )
            findPreHashValueWithImplementation( //Wdk3cjVkk3zRRBNwx8fNn2afkyU9z6uMUK/607GNZF4=
                    passwordString3,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPreHashValue_kotlinSerial() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Serial Implementation findPreHashValue - test run ${i} - ")

            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString1)
                            )
                    )
            )

            findPreHashValueWithImplementation(
                    passwordString1,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPreHashValue_javaMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findPreHashValue - test run ${i} - ")

            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString1)
                            )
                    )
            )

            findPreHashValueWithImplementation(
                    passwordString1,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPreHashValue_kotlinMultiThread() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Serial Implementation findPreHashValue - test run ${i} - ")

            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString1)
                            )
                    )
            )

            findPreHashValueWithImplementation(
                    passwordString1,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPreHashValue_javaCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            print("Java Serial Implementation findPreHashValue - test run ${i} - ")

            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString1)
                            )
                    )
            )

            findPreHashValueWithImplementation(
                    passwordString1,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
        }
    }

    @Test
    fun findPreHashValue_kotlinCoRoutine() {
        for (i in 1..TEST_ITERATIONS) {
            print("Kotlin Serial Implementation findPreHashValue - test run ${i} - ")

            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedValue = String(
                    Base64.getEncoder().encode(
                            messageDigest.digest(
                                    getBytes(passwordString1)
                            )
                    )
            )

            findPreHashValueWithImplementation(
                    passwordString1,
                    hashedValue,
                    ParallelProblemsJavaSerialImpl()
            )
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

        println("Largest number generated was $largestNumberGenerated")

        return largestNumberGenerated
    }

    private fun findLargestNumberInArrayWithImplementation(twoDNumberArray: Array<IntArray>, parallelProblems: ParallelProblems) {

        val startTime = System.currentTimeMillis()
        val largestNumber = parallelProblems.findLargestNumberInAnArray(twoDNumberArray)
        val endTime = System.currentTimeMillis()
        println("${endTime - startTime}ms")

        assertEquals(largestNumberGenerated, largestNumber)
    }

    private fun findPrimeFactorPairWithImplementation(primePairProduct: Long, primeFactorSmall: Long, primeFactorLarge: Long, parallelProblems: ParallelProblems) {
        val startTime = System.currentTimeMillis()
        val primePair = parallelProblems.findPrimeFactors(primePairProduct)
        val endTime = System.currentTimeMillis()
        println("${endTime - startTime}ms")

        assertEquals(2, primePair.size)
        assertEquals(primePair[0], primeFactorSmall)
        assertEquals(primePair[1], primeFactorLarge)
    }

    private fun findPreHashValueWithImplementation(preHashedValue: String, hashedValue: String, parallelProblems: ParallelProblems) {
        val startTime = System.currentTimeMillis()
        val solvedPreHashValue = parallelProblems.findPreHashValueFromHash(hashedValue, symbolSet)
        val endTime = System.currentTimeMillis()
        println("${endTime - startTime}ms")

        assertEquals(preHashedValue, solvedPreHashValue)
    }

    private fun print2DArray(array: Array<IntArray>) {
        for (x in array.indices) {
            for (y in array.indices) {
                print("${array[x][y]} ")
            }
            println("")
        }
    }


}
