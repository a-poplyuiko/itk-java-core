# ITK Java Core - Task 2: Frequency Analysis

## Task description

Write a method that takes an array of elements as input and returns a
`Map` where the keys are the elements and the values are the number of
times each element appears in the array.

Pay attention to the specifics of generic arrays when using
parameterization.

## Background: generics and arrays

A well-known limitation of Java generics is that **generic arrays cannot
be created directly**:

    T[] array = new T[10];   // compile-time error

The reason is that generics in Java are implemented via **type erasure**:
at runtime the JVM does not know the actual type `T`. Creating an array
would require the runtime type information, which is not available.

Two consequences follow from this:

1. **You can accept a `T[]` as a parameter.** The caller is responsible
   for creating the array with a concrete type (e.g. `String[]`,
   `Integer[]`). The method itself just receives it.
2. **You cannot create a `T[]` inside a generic method** without an
   explicit `Array.newInstance(Class<T>, int)` or a `Class<T>` token.
   This is why many generic utilities take an array or a `Class<T>`
   argument instead of allocating one internally.

In this task we only **accept** an array, so no array creation is needed
inside the method.

## Implementation idea

The frequency counter is implemented with a single pass over the array:

    Map<T, Integer> frequencies = new HashMap<>();
    for (T element : elements) {
        frequencies.merge(element, 1, Integer::sum);
    }

`Map.merge(key, value, remappingFunction)` behaves as follows:

- if the key is absent, it stores `value` (here `1`);
- if the key is present, it applies `remappingFunction` to the old and
  new values (here `Integer::sum`) and stores the result.

This avoids explicit `containsKey` / `get` / `put` checks and keeps the
loop concise.

## Edge cases

- **`null` array** — returns an empty map.
- **Empty array** — returns an empty map.
- **`null` elements inside the array** — allowed. `HashMap` accepts one
  `null` key, and `merge` treats it as a normal key, so the method
  counts `null` occurrences correctly.

## Project structure

    task2-frequency-analysis/
    |-- README.md
    |-- pom.xml
    `-- src/main/java/com/itk/frequency/
        |-- FrequencyAnalyzer.java
        `-- FrequencyAnalyzerTest.java

## How to run

1. Go to the task directory:

       cd task2-frequency-analysis

2. Compile the project:

       mvn clean compile

3. Run the demo:

       mvn exec:java

Expected output:

    Words: {apple=3, banana=2, cherry=1}
    Numbers: {1=1, 2=2, 3=3, 4=4}
    Chars: {a=3, b=2, c=1}
    Empty: {}
    Null: {}
    With nulls: {null=3, x=2}

## Example usage

    String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
    Map<String, Integer> frequencies = FrequencyAnalyzer.countFrequencies(words);
    System.out.println(frequencies);   // {apple=3, banana=2, cherry=1}

## Method signature

    public static <T> Map<T, Integer> countFrequencies(T[] elements)

| Parameter  | Type              | Description                            |
|------------|-------------------|----------------------------------------|
| `elements` | `T[]`             | Input array; may be null or empty      |
| returns    | `Map<T, Integer>` | Frequencies of elements                |

## References

- [Java Generics and Arrays (Oracle tutorial)](<https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays>)
- [Map.merge (Java SE API)](<https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html#merge(K,V,java.util.function.BiFunction)>)
