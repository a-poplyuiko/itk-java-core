package com.itk.product;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class DataAggregator {

    private static final double PRICE_FALLBACK = 0.0;
    private static final String DESCRIPTION_FALLBACK = "Нет данных";
    private static final double RATING_FALLBACK = 0.0;

    public ProductInfo aggregateProductInfo(String productName) {
        CompletableFuture<Double> priceFuture =
                CompletableFuture.supplyAsync(this::fetchPrice)
                        .exceptionally(ex -> PRICE_FALLBACK);

        CompletableFuture<String> descriptionFuture =
                CompletableFuture.supplyAsync(this::fetchDescription)
                        .exceptionally(ex -> DESCRIPTION_FALLBACK);

        CompletableFuture<Double> ratingFuture =
                CompletableFuture.supplyAsync(this::fetchRating)
                        .exceptionally(ex -> RATING_FALLBACK);

        return priceFuture
                .thenCombine(descriptionFuture, (price, description) ->
                        new Object[]{price, description})
                .thenCombine(ratingFuture, (pair, rating) ->
                        new ProductInfo(
                                productName,
                                (double) pair[0],
                                (String) pair[1],
                                rating))
                .join();
    }

    private double fetchPrice() {
        sleep();
        maybeFail("price service");
        return 899.99;
    }

    private String fetchDescription() {
        sleep();
        maybeFail("description service");
        return "Мощный ноутбук для работы и игр";
    }

    private double fetchRating() {
        sleep();
        maybeFail("rating service");
        return 4.7;
    }

    private void sleep() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 3001));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("interrupted", e);
        }
    }

    private void maybeFail(String serviceName) {
        if (ThreadLocalRandom.current().nextDouble() < 0.2) {
            throw new RuntimeException(serviceName + " unavailable");
        }
    }
}
