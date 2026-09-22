package com.itk.frequency;

import java.util.Map;

/**
 * Demonstrates the behaviour of {@link FrequencyAnalyzer}.
 */
public class FrequencyAnalyzerTest {

    public static void main(String[] args) {
        // 1. Strings
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        Map<String, Integer> wordFrequencies = FrequencyAnalyzer.countFrequencies(words);
        System.out.println("Words: " + wordFrequencies);
        // {apple=3, banana=2, cherry=1}

        // 2. Integers
        Integer[] numbers = {1, 2, 2, 3, 3, 3, 4, 4, 4, 4};
        Map<Integer, Integer> numberFrequencies = FrequencyAnalyzer.countFrequencies(numbers);
        System.out.println("Numbers: " + numberFrequencies);
        // {1=1, 2=2, 3=3, 4=4}

        // 3. Characters
        Character[] chars = {'a', 'b', 'a', 'c', 'a', 'b'};
        Map<Character, Integer> charFrequencies = FrequencyAnalyzer.countFrequencies(chars);
        System.out.println("Chars: " + charFrequencies);
        // {a=3, b=2, c=1}

        // 4. Empty array
        String[] empty = {};
        System.out.println("Empty: " + FrequencyAnalyzer.countFrequencies(empty));
        // {}

        // 5. Null array
        System.out.println("Null: " + FrequencyAnalyzer.countFrequencies(null));
        // {}

        // 6. Array with null elements
        String[] withNulls = {"x", null, "x", null, null};
        Map<String, Integer> nullFrequencies = FrequencyAnalyzer.countFrequencies(withNulls);
        System.out.println("With nulls: " + nullFrequencies);
        // {null=3, x=2}
    }
}