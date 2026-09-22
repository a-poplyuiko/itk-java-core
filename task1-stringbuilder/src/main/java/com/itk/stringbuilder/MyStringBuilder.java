package com.itk.stringbuilder;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A simplified implementation of {@link StringBuilder} with support
 * for undoing the last operation via {@link #undo()}.
 *
 * <p>The class demonstrates the <b>Snapshot (Memento)</b> design pattern:
 * before every mutating operation, the current state of the buffer is saved
 * to a history stack. {@link #undo()} pops the latest snapshot and restores
 * the buffer from it.</p>
 *
 * <p>Only a subset of {@code StringBuilder} methods is implemented on purpose —
 * the goal of the task is to demonstrate the pattern, not to reimplement
 * the whole JDK class.</p>
 */
public class MyStringBuilder {

    /**
     * Internal buffer holding the current state of the string.
     */
    private final StringBuilder buffer;

    /**
     * Stack of snapshots used to support {@link #undo()}.
     * Each element represents a previous state of the buffer.
     */
    private final Deque<String> history;

    /**
     * Creates an empty {@code MyStringBuilder}.
     */
    public MyStringBuilder() {
        this.buffer = new StringBuilder();
        this.history = new ArrayDeque<>();
    }

    /**
     * Creates a {@code MyStringBuilder} initialized with the given string.
     *
     * @param initial the initial content
     */
    public MyStringBuilder(String initial) {
        this.buffer = new StringBuilder(initial);
        this.history = new ArrayDeque<>();
    }

    /**
     * Saves the current state of the buffer to the history stack.
     *
     * <p>This is the core of the Snapshot pattern — we take a "photo"
     * of the current state <i>before</i> mutating it, so it can later
     * be restored by {@link #undo()}.</p>
     */
    private void saveSnapshot() {
        history.push(buffer.toString());
    }

    /**
     * Appends the given string to the end of the buffer.
     *
     * @param str the string to append
     * @return this instance for method chaining
     */
    public MyStringBuilder append(String str) {
        saveSnapshot();
        buffer.append(str);
        return this;
    }

    /**
     * Appends the given character to the end of the buffer.
     *
     * @param ch the character to append
     * @return this instance for method chaining
     */
    public MyStringBuilder append(char ch) {
        saveSnapshot();
        buffer.append(ch);
        return this;
    }

    /**
     * Deletes the character at the specified index.
     *
     * @param index the index of the character to delete
     * @return this instance for method chaining
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public MyStringBuilder deleteCharAt(int index) {
        saveSnapshot();
        buffer.deleteCharAt(index);
        return this;
    }

    /**
     * Deletes the substring in the range {@code [start, end)}.
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return this instance for method chaining
     */
    public MyStringBuilder delete(int start, int end) {
        saveSnapshot();
        buffer.delete(start, end);
        return this;
    }

    /**
     * Inserts the given string at the specified position.
     *
     * @param index the position to insert at
     * @param str   the string to insert
     * @return this instance for method chaining
     */
    public MyStringBuilder insert(int index, String str) {
        saveSnapshot();
        buffer.insert(index, str);
        return this;
    }

    /**
     * Inserts the given character at the specified position.
     *
     * @param index the position to insert at
     * @param ch    the character to insert
     * @return this instance for method chaining
     */
    public MyStringBuilder insert(int index, char ch) {
        saveSnapshot();
        buffer.insert(index, ch);
        return this;
    }

    /**
     * Replaces the substring in the range {@code [start, end)}
     * with the given string.
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @param str   the replacement string
     * @return this instance for method chaining
     */
    public MyStringBuilder replace(int start, int end, String str) {
        saveSnapshot();
        buffer.replace(start, end, str);
        return this;
    }

    /**
     * Returns the current length of the buffer.
     *
     * @return the number of characters
     */
    public int length() {
        return buffer.length();
    }

    /**
     * Returns the character at the specified index.
     *
     * @param index the index of the character
     * @return the character at the given index
     */
    public char charAt(int index) {
        return buffer.charAt(index);
    }

    /**
     * Undoes the last mutating operation.
     *
     * <p>Restores the buffer from the most recent snapshot in the history.
     * If the history is empty, the call has no effect.</p>
     */
    public void undo() {
        if (!history.isEmpty()) {
            String previousState = history.pop();
            buffer.setLength(0);
            buffer.append(previousState);
        }
    }

    /**
     * Returns the current content as a string.
     *
     * @return the string representation of the buffer
     */
    @Override
    public String toString() {
        return buffer.toString();
    }
}