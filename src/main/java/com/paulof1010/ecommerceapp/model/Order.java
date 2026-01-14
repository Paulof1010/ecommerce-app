package com.paulof1010.ecommerceapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
public class Order {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    @OneToMany (cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated (EnumType.STRING)
    private OrderStatus status;

    private final LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    private BigDecimal total;

    public Order(User user, List<OrderItem> items) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (items == null) {
            throw new IllegalArgumentException("List of items cannot be null");
        }
        this.user = user;
        this.items = items;
        status = OrderStatus.DRAFT;
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        total = items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //METHODS
    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Cannot add items to an order that is not in DRAFT state");
        }

        for (OrderItem existingItem : items) {
            if (existingItem.getProduct().equals(item.getProduct())) {
                existingItem.increaseQuantity(item.getQuantity());
                refreshTotalsAndTimestamp();
                return;
            }
        }
        items.add(item);
        refreshTotalsAndTimestamp();
    }

    public void removeItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Cannot remove items from an order that is not in DRAFT state");
        }
        for (OrderItem existingItem : items) {
            if (existingItem.getProduct().equals(item.getProduct())) {
                int quantityToRemove = item.getQuantity();
                if (quantityToRemove >= existingItem.getQuantity()) {
                    items.remove(existingItem);
                } else {
                    existingItem.decreaseQuantity(quantityToRemove);
                }
                refreshTotalsAndTimestamp();
                return;
            }
        }
        throw new IllegalArgumentException("Item not found in the order");
    }

    // Method to recalc total and update timestamp
    private void refreshTotalsAndTimestamp() {
        total = items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        updatedAt = LocalDateTime.now();
    }

    public void pay() {
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Order can only be paid if it is in DRAFT state");
        }
        for (OrderItem item : items) {
            item.getProduct().decreaseStock(item.getQuantity());
        }
        status = OrderStatus.PAID;
        updatedAt = LocalDateTime.now();
    }

    public void ship() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Order can only be shipped if it is in PAID state");
        }
        status = OrderStatus.SHIPPED;
        updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel shipped or already cancelled order");
        }
        if (status == OrderStatus.PAID) {
            items.forEach(i -> i.getProduct().increaseStock(i.getQuantity()));
        }
        status = OrderStatus.CANCELLED;
        updatedAt = LocalDateTime.now();
    }

    //GETTERS
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    public int getTotalItems() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }
}