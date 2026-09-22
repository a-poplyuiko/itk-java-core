package com.itk.stringbuilder;

/**
 * Demonstrates the behaviour of {@link MyStringBuilder},
 * including the {@code undo()} operation.
 */
public class MyStringBuilderTest {

    public static void main(String[] args) {
        MyStringBuilder sb = new MyStringBuilder("Hello");

        System.out.println("Initial: " + sb);                        // Hello

        sb.append(" World");
        System.out.println("After append: " + sb);                   // Hello World

        sb.append("!");
        System.out.println("After append: " + sb);                   // Hello World!

        sb.deleteCharAt(0);
        System.out.println("After deleteCharAt: " + sb);             // ello World!

        sb.undo();
        System.out.println("After undo (delete cancelled): " + sb);  // Hello World!

        sb.undo();
        System.out.println("After undo (append cancelled): " + sb);  // Hello World

        sb.undo();
        System.out.println("After undo (append cancelled): " + sb);  // Hello

        sb.undo();
        System.out.println("After undo (history empty): " + sb);     // Hello
    }
}