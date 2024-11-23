package com.example.springone.CartPackage.HelperClasses;

import java.io.Serializable;
import java.util.Objects;

public class CartProductId implements Serializable {
    private int cart;
    private int product;

    // Default constructor
    public CartProductId() {}

    public CartProductId(int cart, int product) {
        this.cart = cart;
        this.product = product;
    }

    // Getters and Setters
    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProductId that = (CartProductId) o;
        return cart == that.cart && product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, product);
    }
}
