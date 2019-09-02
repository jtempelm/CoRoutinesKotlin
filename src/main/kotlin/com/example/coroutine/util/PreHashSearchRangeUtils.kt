package com.example.coroutine.util

import com.example.coroutine.util.Constants.MAX_HASH_LENGTH
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

internal fun findPreHashValueInRangeFromHash(startOfRange: Int, scanRangeSize: Int, hash: String, sortedSymbolSet: CharArray): String {
    if (hash.isEmpty() || sortedSymbolSet.isEmpty()) {
        return ""
    }

    var passwordFound = false
    val hashGuess = getNthHashGuess(startOfRange, sortedSymbolSet)
    val endOfRangeGuess = getNthHashGuess(startOfRange + scanRangeSize, sortedSymbolSet)

    var symbolSetIndex = 0
    if (hashGuess.toString() == "") {
        hashGuess.append(sortedSymbolSet[0])
    } else {
        symbolSetIndex = getSymbolIndex(hashGuess[0], sortedSymbolSet)
    }

    while (!passwordFound
            && hashGuess.length <= MAX_HASH_LENGTH
            && hashGuess.toString() != endOfRangeGuess.toString()) {
        symbolSetIndex = incrementHashStringInParameterRecursive(hashGuess, sortedSymbolSet, symbolSetIndex)
        passwordFound = isGuessCorrect(hash, hashGuess)
    }

    return if (passwordFound) {
        hashGuess.toString()
    } else {
        ""
    }
}

private fun getNthHashGuess(startOfRange: Int, sortedSymbolSet: CharArray): StringBuilder {
    val base = sortedSymbolSet.size
    var remainder = startOfRange
    var symbolIndex: Int
    val hashGuess = StringBuilder()
    var numberHasStarted = false

    for (i in MAX_HASH_LENGTH downTo 0) {
        val nthBase = Math.pow(base.toDouble(), i.toDouble()).toInt()
        if (nthBase <= remainder) {
            symbolIndex = remainder / nthBase
            remainder -= (symbolIndex * Math.pow(base.toDouble(), i.toDouble())).toInt()

            hashGuess.append(sortedSymbolSet[symbolIndex])
            numberHasStarted = true
        } else if (numberHasStarted) {
            hashGuess.append(sortedSymbolSet[0])
        }
    }

    return hashGuess
}

private fun incrementHashStringInParameterRecursive(hashGuess: StringBuilder, symbolSet: CharArray, symbolSetIndex: Int): Int {
    var symbolSetIndex = symbolSetIndex
    if (symbolSetIndex < symbolSet.size) {
        hashGuess.setCharAt(0, symbolSet[symbolSetIndex])
        symbolSetIndex++
    } else {
        symbolSetIndex = 1 //below already sets symbolSet[0] to the first char so we want to start iterating on the next one
        resolveIncrementWithRecursion(hashGuess, 0, symbolSet)
    }

    return symbolSetIndex
}

private fun resolveIncrementWithRecursion(hashGuess: StringBuilder, index: Int, symbolSet: CharArray) {
    if (index < hashGuess.length) {
        if (hashGuess[index] == symbolSet[symbolSet.size - 1]) { //think a digital counter going up an order of magnitude on 9
            hashGuess.setCharAt(index, symbolSet[0])
            resolveIncrementWithRecursion(hashGuess, index + 1, symbolSet)
        } else {
            hashGuess.setCharAt(
                    index,
                    symbolSet[getSymbolIndex(hashGuess[index], symbolSet) + 1]
            )
        }
    } else {
        hashGuess.append(symbolSet[0])
    }
}

private fun getSymbolIndex(symbol: Char, symbolSet: CharArray): Int { //Because I'm lazy, and I don't want to maintain the state of the n+1th index. Really I should refactor this and use indexOf() or something
    return Arrays.binarySearch(symbolSet, symbol)
}

private fun isGuessCorrect(hash: String, hashGuess: StringBuilder): Boolean {
    val hashedValue = calculateHash(hashGuess)

    return hashedValue == hash
}

private fun calculateHash(hashGuess: StringBuilder): String {
    var messageDigest: MessageDigest? = null
    try {
        messageDigest = MessageDigest.getInstance("SHA-256")
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return String(
            Base64.getEncoder().encode(
                    messageDigest!!.digest(
                            hashGuess.toString().toByteArray()
                    )
            )
    )
}