# Task 4: BlockingQueue

## Что нужно

Блокирующая очередь фиксированного размера с методами `enqueue`,
`dequeue` и `size` для producer–consumer в пуле потоков.
Если очередь пуста, `dequeue` блокирует поток до появления элемента.

## Как решал

`wait()` / `notifyAll()` под `synchronized` на самом объекте очереди.
Условия проверяются в `while`, а не в `if` — защита от spurious wakeup.
`enqueue` блокируется, если очередь полна; `dequeue` — если пуста.

## Запуск

    mvn compile
    mvn exec:java

Вывод — чередование `produced` / `consumed`, `size` колеблется от 0 до capacity.
