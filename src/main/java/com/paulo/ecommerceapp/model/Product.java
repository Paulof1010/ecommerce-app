package com.paulo.ecommerceapp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Product {

    private Integer id;
    @Setter
    private String name;
    @Setter
    private String description;
    private final BigDecimal price;
    private Integer stock;
    private Boolean active;
    private final LocalDateTime createdAt;

    public Product(String name, BigDecimal price, Integer stock) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must be greater or equal to zero");
        }

        this.name = name;
        this.price = price;
        this.stock = stock;
        active = true;
        createdAt = LocalDateTime.now();
    }

    //METHODS
    public boolean hasStock() {
        return stock > 0;
    }

    public void decreaseStock(int quantity) {
        if (quantity > 0 && quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock.");
        }
    }
    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        } else {
            stock += quantity;
        }
    }

    public void deactivate() {
        active = false;
    }
}
