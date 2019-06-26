package com.example.coroutine.util;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorizationSearchUtilsJava {

    public static List<Long> findPrimeFactorsInRange(final long startOfRange, final long endOfRange) {
        final List<Long> primeFactors = new ArrayList<Long>(); //TODO can't use this algorithm :/

        if (endOfRange < 2) {
            return primeFactors;
        }

        Long remainder = endOfRange;
        while (remainder % 2 == 0L) {
            if (!primeFactors.contains(2L)) {
                primeFactors.add(2L);
            }
            remainder /= 2L;
        }

        Long i = startOfRange < 3L ? 3L : startOfRange;
        while (i <= (remainder / i)) {
            while (remainder % i == 0) {
                if (!primeFactors.contains(i)) {
                    primeFactors.add(i);
                }
                remainder /= i;
            }

            i += 2;
        }

        if (remainder > 1) {
            primeFactors.add(remainder);
        }

        return primeFactors;
    }
}
