# Java Core Task #6: CompletableFuture

## Задача

Асинхронно собрать данные о товаре из трёх сервисов (цена, описание,
рейтинг). Каждый может упасть с вероятностью 20%. Нужны fallback-значения.

## Решение

- Три `supplyAsync` запускаются параллельно на `ForkJoinPool.commonPool()`.
- `exceptionally` подставляет значения по умолчанию при сбое.
- Результаты объединяются через `thenCombine` в два шага.
- `join()` в конце ждёт готовый `ProductInfo`.

## Запуск

    mvn compile
    mvn exec:java

Вывод:

    ProductInfo[name=Ноутбук, price=899.99, description='Нет данных', rating=4.7]
    elapsed: ~3000 ms
    