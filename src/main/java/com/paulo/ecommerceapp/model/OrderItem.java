package com.paulo.ecommerceapp.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderItem {

    private Integer id;
    private final Product product;
    private Integer quantity;
    private final BigDecimal price;
    private final LocalDateTime createdAt;

    public OrderItem(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Quantity must be less than stock available");
        }
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
        createdAt = LocalDateTime.now();
    }

    //METHODS
    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void increaseQuantity(int q) {
        if (q <= 0) {
            throw new IllegalArgumentException("Increase quantity must be greater than zero");
        }
        if (quantity + q > product.getStock()) {
            throw new IllegalArgumentException("Quantity cannot be greater than stock");
        } else {
            quantity += q;
        }
    }
    public void decreaseQuantity(int q) {
        if (q <= 0) {
            throw new IllegalArgumentException("Decrease quantity must be greater than zero");
        }
        if (quantity - q < 1) {
            throw new IllegalArgumentException("Quantity cannot be less than one");
        } else {
            quantity -= q;
        }
    }
}
