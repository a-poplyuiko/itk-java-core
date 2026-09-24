package com.itk.product;

public record ProductInfo(
        String name,
        double price,
        String description,
        double rating
) {
    @Override
    public String toString() {
        return "ProductInfo{name='" + name + "', price=" + price
                + ", description='" + description + "', rating=" + rating + "}";
    }
}
