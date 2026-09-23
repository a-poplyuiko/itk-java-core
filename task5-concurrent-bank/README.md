# Java Core Task #5: Concurrent bank

## Что нужно

Многопоточный банк: счета с deposit/withdraw/getBalance, атомарные
переводы между счетами, общий баланс.

## Как решал

- `BankAccount` — `AtomicLong` для баланса, `ReentrantLock` для координации
  пары счетов в переводе.
- `ConcurrentBank.transfer`:
  - **lock ordering** по `id` — разрывает circular wait;
  - **tryLock с таймаутом** — разрывает hold-and-wait;
  - освобождение локов в `finally` — даже при исключении.
- `createAccount` — `ConcurrentHashMap` + `AtomicLong` для уникальных id.
- `getTotalBalance` — без глобальной блокировки, через `LongAdder`.

## Deadlock и условия Коффмана

Deadlock возможен только при одновременном выполнении всех четырёх
условий. Достаточно разорвать одно:

- **Mutual exclusion** — остаётся (лок на счёт нужен).
- **Hold and wait** — разрываем `tryLock` с таймаутом.
- **No preemption** — остаётся (Java не отбирает локи).
- **Circular wait** — разрываем lock ordering по `id`.

## Запуск

    mvn compile
    mvn exec:java

Тест: 8 потоков, 10 000 переводов каждый. В конце проверяется, что
суммарный баланс не изменился.
