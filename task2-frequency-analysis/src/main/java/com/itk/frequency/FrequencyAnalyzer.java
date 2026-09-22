package com.itk.frequency;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for counting the frequency of elements in an array.
 *
 * <p>The class demonstrates working with generics and arrays:
 * because generic arrays cannot be created directly ({@code new T[]}
 * is illegal), the method accepts a {@code T[]} and returns a
 * {@code Map<T, Integer>}.</p>
 */
public final class FrequencyAnalyzer {

    private FrequencyAnalyzer() {
        // utility class, no instances
    }

    /**
     * Counts how many times each element appears in the given array.
     *
     * <p>Uses {@link Map#merge(Object, Object, java.util.function.BiFunction)}
     * to accumulate counters without explicit null checks.</p>
     *
     * @param elements the input array; may be {@code null} or empty
     * @param <T>      the type of elements
     * @return a map where keys are the distinct elements of the array
     *         and values are their frequencies; empty map if the input
     *         is {@code null} or empty
     */
    public static <T> Map<T, Integer> countFrequencies(T[] elements) {
        Map<T, Integer> frequencies = new HashMap<>();

        if (elements == null || elements.length == 0) {
            return frequencies;
        }

        for (T element : elements) {
            frequencies.merge(element, 1, Integer::sum);
        }

        return frequencies;
    }
}