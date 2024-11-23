package com.example.springone.CartPackage.Entity;

import com.example.springone.CartPackage.HelperClasses.CartProductId;
import com.example.springone.ProductsPackage.Entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Cart_Product")
@IdClass(CartProductId.class) // Specify the composite key class
public class CartProductEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Ensure cart is mandatory
    @JoinColumn(name = "cartID", nullable = false) // Maps to CartEntity's primary key
    private CartEntity cart;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Ensure product is mandatory
    @JoinColumn(name = "productID", nullable = false) // Maps to ProductEntity's primary key
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    // Constructors
    public CartProductEntity() {}

    public CartProductEntity(CartEntity cart, ProductEntity product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
