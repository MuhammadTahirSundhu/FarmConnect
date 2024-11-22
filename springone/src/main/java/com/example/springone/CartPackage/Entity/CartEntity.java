package com.example.springone.CartPackage.Entity;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
@Entity
@Table(name = "Cart")
public class CartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartID", nullable = false)
    private int cartID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumerID", nullable = false)
    private ConsumerEntity consumer;

    // Constructors
    public CartEntity() {}

    public CartEntity(ConsumerEntity consumer) {
        this.consumer = consumer;
    }

    // Getters and Setters
    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public ConsumerEntity getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerEntity consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID=" + cartID +
                ", consumer=" + (consumer != null ? consumer.getConsumerID() : null) +
                '}';
    }
}
