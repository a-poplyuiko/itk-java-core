package com.itk.product;

public class ProductInfoDemo {

    public static void main(String[] args) {
        DataAggregator aggregator = new DataAggregator();

        long start = System.currentTimeMillis();
        ProductInfo info = aggregator.aggregateProductInfo("Ноутбук");
        long elapsed = System.currentTimeMillis() - start;

        System.out.println(info);
        System.out.println("elapsed: " + elapsed + " ms");
    }
}
