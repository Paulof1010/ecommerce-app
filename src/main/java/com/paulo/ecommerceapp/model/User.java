package com.paulo.ecommerceapp.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {

    private Integer id;
    private final String email;
    private String password;
    private final Role role;
    private final LocalDateTime createdAt;

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        createdAt = LocalDateTime.now();
    }

    // METHODS
    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

    public boolean canAccessOrder(Order order) {
        return order.getUser() == this;
    }

}
