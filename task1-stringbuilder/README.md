# ITK Java Core - Task 1: Custom StringBuilder with undo()

## Task description

Study the internal implementation of the standard StringBuilder class
and write your own simplified version with one extra method - undo().

Before implementing, read about the Snapshot pattern and apply it in
your solution. Do not take the pattern literally - it can be implemented
in different ways while preserving its core idea.

Note: you do not need to reimplement every method of the JDK
StringBuilder. The goal of this task is to demonstrate understanding
of the Snapshot pattern, not to duplicate the whole JDK class.

## How it works: the Snapshot / Memento pattern

The Snapshot pattern (also known as Memento) allows an object to save
its internal state and restore it later without violating encapsulation.

In its classic form, the pattern has three roles:

| Role       | Responsibility                                                 | In this project          |
|------------|----------------------------------------------------------------|--------------------------|
| Originator | The object whose state we want to save and restore.            | MyStringBuilder          |
| Memento    | A snapshot of the originator's state.                          | A plain String           |
| Caretaker  | Stores snapshots but never inspects or modifies their content. | Deque< String > history  |

### Core idea

Before every mutating operation (append, delete, insert, replace, ...)
the current state of the buffer is captured and pushed onto a history
stack. This is done by the private method saveSnapshot():

    private void saveSnapshot() {
        history.push(buffer.toString());
    }

When undo() is called, the most recent snapshot is popped from the stack
and used to restore the buffer:

    public void undo() {
        if (!history.isEmpty()) {
            String previousState = history.pop();
            buffer.setLength(0);
            buffer.append(previousState);
        }
    }

If the history is empty, undo() does nothing.

### Why this is flexible

As the task states, the Snapshot pattern can be implemented in different
ways. A few alternatives:

- Snapshot of the whole string (used here) - simple and clear, but
  memory-heavy for long strings.
- Snapshot of only the changed part - more efficient, but more complex.
- Command-based undo - instead of storing states, store reverse
  operations for each command. This is closer to the Command pattern
  and is often used in real editors.

For this educational task, storing full string snapshots is the most
visual and straightforward approach.

## Project structure

    itk-java-core/
    |-- README.md
    `-- task1-stringbuilder/
        `-- src/
            `-- main/
                `-- java/
                    `-- com/
                        `-- itk/
                            `-- stringbuilder/
                                |-- MyStringBuilder.java
                                `-- MyStringBuilderTest.java

## How to run

1. Clone the repository:

       git clone https://github.com/a-poplyuiko/itk-java-core.git
       cd itk-java-core

2. Checkout the task branch:

       git checkout java-core-task1-stringbuilder

3. Compile and run from the task1-stringbuilder directory:

       javac src/main/java/com/itk/stringbuilder/*.java
       java -cp src com.itk.stringbuilder.MyStringBuilderTest

Expected output:

    Initial: Hello
    After append: Hello World
    After append: Hello World!
    After deleteCharAt: ello World!
    After undo (delete cancelled): Hello World!
    After undo (append cancelled): Hello World
    After undo (append cancelled): Hello
    After undo (history empty): Hello

## Example usage

    MyStringBuilder sb = new MyStringBuilder("Hello");

    sb.append(" World");
    sb.append("!");
    System.out.println(sb);   // Hello World!

    sb.deleteCharAt(0);
    System.out.println(sb);   // ello World!

    sb.undo();
    System.out.println(sb);   // Hello World!

    sb.undo();
    System.out.println(sb);   // Hello World

    sb.undo();
    System.out.println(sb);   // Hello

## Implemented methods

| Method                       | Description                                 |
|------------------------------|---------------------------------------------|
| append(String)               | Appends a string to the end.                |
| append(char)                 | Appends a character to the end.             |
| insert(int, String)          | Inserts a string at the given position.     |
| insert(int, char)            | Inserts a character at the given position.  |
| delete(int, int)             | Deletes a range of characters.              |
| deleteCharAt(int)            | Deletes a character at the given index.     |
| replace(int, int, String)    | Replaces a range of characters.             |
| length()                     | Returns the current length.                 |
| charAt(int)                  | Returns the character at the given index.   |
| undo()                       | Restores the previous state (Snapshot).     |
| toString()                   | Returns the current content as a string.    |

## References

    - Memento pattern (Snapshot) — Wikipedia (RU):
    https://ru.wikipedia.org/wiki/Хранитель_(шаблон_проектирования)

    - java-design-patterns — Memento example (Java source code):
    https://github.com/iluwatar/java-design-patterns/tree/master/memento

    - java.lang.StringBuilder — Oracle Java SE API:
    https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/StringBuilder.html
