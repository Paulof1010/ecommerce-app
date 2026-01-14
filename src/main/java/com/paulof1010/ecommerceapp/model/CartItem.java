package com.paulof1010.ecommerceapp.model;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Quantity exceeds available stock");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (quantity + amount > product.getStock()) {
            throw new IllegalArgumentException("Quantity exceeds available stock");
        }
        quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (quantity - amount < 1) {
            throw new IllegalArgumentException("Quantity cannot be less than one");
        }
        quantity -= amount;
    }

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

