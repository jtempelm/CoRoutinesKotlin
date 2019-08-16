package com.example.coroutine.util;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static com.example.coroutine.util.Constants.MAX_HASH_LENGTH;

public class PreHashSearchRangeUtils {

    public static String findPreHashValueInRangeFromHash(final int startOfRange, final int scanRangeSize, final String hash, final char[] sortedSymbolSet) {
        if (hash.isEmpty() || sortedSymbolSet.length == 0) {
            return "";
        }

        boolean passwordFound = false;
        final StringBuilder hashGuess = getNthHashGuess(startOfRange, sortedSymbolSet);
        final StringBuilder endOfRangeGuess = getNthHashGuess(startOfRange + scanRangeSize, sortedSymbolSet);

        int symbolSetIndex = 0;
        if (hashGuess.toString().equals("")) {
            hashGuess.append(sortedSymbolSet[0]);
        } else {
            symbolSetIndex = getSymbolIndex(hashGuess.charAt(0), sortedSymbolSet);
        }

        while (!passwordFound
            && hashGuess.length() <= MAX_HASH_LENGTH
            && !hashGuess.toString().equals(endOfRangeGuess.toString())) {
            symbolSetIndex = incrementHashStringInParameterRecursive(hashGuess, sortedSymbolSet, symbolSetIndex);
            passwordFound = isGuessCorrect(hash, hashGuess);
        }

        if (passwordFound) {
            return hashGuess.toString();
        } else {
            return "";
        }
    }

    private static StringBuilder getNthHashGuess(final int startOfRange, final char[] sortedSymbolSet) {
        final int base = sortedSymbolSet.length;
        int remainder = startOfRange;
        int symbolIndex;
        final StringBuilder hashGuess = new StringBuilder();
        boolean numberHasStarted = false;

        for (int i = MAX_HASH_LENGTH; i >= 0; i--) {
            final int nthBase = (int) Math.pow(base, i);
            if (nthBase <= remainder) {
                symbolIndex = remainder / nthBase;
                remainder -= symbolIndex * Math.pow(base, i);

                hashGuess.append(sortedSymbolSet[symbolIndex]);
                numberHasStarted = true;
            } else if (numberHasStarted) {
                hashGuess.append(sortedSymbolSet[0]);
            }
        }

        return hashGuess;
    }

    private static int incrementHashStringInParameterRecursive(final StringBuilder hashGuess, final char[] symbolSet, int symbolSetIndex) {
        if (symbolSetIndex < symbolSet.length) {
            hashGuess.setCharAt(0, (symbolSet[symbolSetIndex]));
            symbolSetIndex++;
        } else {
            symbolSetIndex = 1; //below already sets symbolSet[0] to the first char so we want to start iterating on the next one
            resolveIncrementWithRecursion(hashGuess, 0, symbolSet);
        }

        return symbolSetIndex;
    }

    private static void resolveIncrementWithRecursion(final StringBuilder hashGuess, final int index, final char[] symbolSet) {
        if (index < hashGuess.length()) {
            if (hashGuess.charAt(index) == symbolSet[symbolSet.length - 1]) { //think a digital counter going up an order of magnitude on 9
                hashGuess.setCharAt(index, symbolSet[0]);
                resolveIncrementWithRecursion(hashGuess, index + 1, symbolSet);
            } else {
                hashGuess.setCharAt(
                    index,
                    symbolSet[getSymbolIndex(hashGuess.charAt(index), symbolSet) + 1]
                );
            }
        } else {
            hashGuess.append(symbolSet[0]);
        }
    }

    private static int getSymbolIndex(final char symbol, final char[] symbolSet) { //Because I'm lazy, and I don't want to maintain the state of the n+1th index. Really I should refactor this and use indexOf() or something
        return Arrays.binarySearch(symbolSet, symbol);
    }

    private static boolean isGuessCorrect(@NotNull final String hash, final StringBuilder hashGuess) {
        final String hashedValue = calculateHash(hashGuess);

        return hashedValue.equals(hash);
    }

    private static String calculateHash(final StringBuilder hashGuess) {
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
