package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.PrimeFactorizationSearchUtilsJava;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.example.coroutine.util.Constants.MAX_HASH_LENGTH;

public class ParallelProblemsJavaSerialImpl implements ParallelProblems {

    @Override
    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(0, array.length, array);
        // return TwoDimensionArraySearchUtilsKotlinKt.findLargestNumberInArrayRange(0, array.length, array); //You can call the kotlin implementation directly from Java
    }

    @NotNull
    @Override
    public List<Long> findPrimeFactors(final long primeProduct) {
        return PrimeFactorizationSearchUtilsJava.findPrimeFactorsInRange(0L, primeProduct);
    }

    @NotNull
    @Override
    public String findPreHashValueFromHash(@NotNull final String hash, @NotNull final char[] symbolSet) { //based off the implementation here: https://stackoverflow.com/questions/9351923/brute-force-performance-java-vs-c-sharp
        if (hash.isEmpty() || symbolSet.length == 0) {
            return "";
        }

        final char[] sortedSymbolSet = symbolSet.clone();
        Arrays.sort(sortedSymbolSet); //our comparison operation later depends upon a sorted sequence of characters when compared to int values

        boolean passwordFound = false;
        final StringBuilder hashGuess = new StringBuilder();
        hashGuess.append(sortedSymbolSet[0]);
        int symbolSetIndex = 0;

        while (!passwordFound && hashGuess.length() <= MAX_HASH_LENGTH) {
            symbolSetIndex = incrementHashStringInParameterRecursive(hashGuess, sortedSymbolSet, symbolSetIndex);
            passwordFound = isGuessCorrect(hash, hashGuess);
        }
        if (passwordFound) {
            return hashGuess.toString();
        } else {
            return "";
        }
    }

    private int incrementHashStringInParameterRecursive(final StringBuilder hashGuess, final char[] symbolSet, int symbolSetIndex) {
        if (symbolSetIndex < symbolSet.length) {
            hashGuess.setCharAt(0, (symbolSet[symbolSetIndex]));
            symbolSetIndex++;
        } else {
            symbolSetIndex = 1; //below already sets symbolSet[0] to the first char so we want to start iterating on the next one
            resolveIncrementWithRecursion(hashGuess, 0, symbolSet);
        }

        return symbolSetIndex;
    }

    private void resolveIncrementWithRecursion(final StringBuilder hashGuess, final int index, final char[] symbolSet) {
        if (index < hashGuess.length()) {
            if (hashGuess.charAt(index) == symbolSet[symbolSet.length - 1]) { //think a digital counter going up an order of magnitude on 9
                hashGuess.setCharAt(index, symbolSet[0]);
                resolveIncrementWithRecursion(hashGuess, index + 1, symbolSet);
            } else {
                hashGuess.setCharAt(index, getNextSymbol(hashGuess.charAt(index), symbolSet));
            }
        } else {
            hashGuess.append(symbolSet[0]);
        }
    }

    private char getNextSymbol(final char symbol, final char[] symbolSet) { //Because I'm lazy, and I don't want to maintain the state of the n+1th index. Really I should refactor this and use indexOf() or something
        final int indexOfSymbol = Arrays.binarySearch(symbolSet, symbol);

        return symbolSet[indexOfSymbol + 1];
    }

    private boolean isGuessCorrect(@NotNull final String hash, final StringBuilder hashGuess) {
//        System.out.println(hashGuess); //DEBUG

        final String hashedValue = calculateHash(hashGuess);

        return hashedValue.equals(hash);
    }

    private String calculateHash(final StringBuilder hashGuess) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new String(
            Base64.getEncoder().encode(
                messageDigest.digest(
                    hashGuess.toString().getBytes()
                )
            )
        );
    }
}