package com.paulof1010.ecommerceapp.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class ShoppingCart {

    private final User user;
    private final List<CartItem> items;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ShoppingCart(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        this.user = user;
        this.items = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    // ───────────────────────────────
    // Cart Behavior
    // ───────────────────────────────

    public void addItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");

        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.increaseQuantity(quantity);
                touch();
                return;
            }
        }

        items.add(new CartItem(product, quantity));
        touch();
    }

    public void removeItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().equals(product)) {
                if (quantity >= item.getQuantity()) {
                    items.remove(i);
                } else {
                    item.decreaseQuantity(quantity);
                }
                touch();
                return;
            }
        }

        throw new IllegalArgumentException("Product not found in cart");
    }

    public void clear() {
        items.clear();
        touch();
    }

    // ───────────────────────────────
    // Calculations
    // ───────────────────────────────

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    // ───────────────────────────────
    // Checkout → Order
    // ───────────────────────────────

    public Order checkout() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : items) {
            orderItems.add(new OrderItem(
                    cartItem.getProduct(),
                    cartItem.getQuantity()
            ));
        }

        Order order = new Order(user, orderItems);
        clear();              // cart is consumed
        return order;
    }

    private void touch() {
        updatedAt = LocalDateTime.now();
    }
}
