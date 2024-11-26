package com.example.springone.OrdersPackage.Entity;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "\"Order\"") // Escaping table name to handle reserved keyword
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    @Column(name = "orderID")
    private int orderID;

    @Column(name = "datePlaced", nullable = false)
    private Timestamp datePlaced;


    @Column(name = "status", nullable = false, length = 100)
    private String status; // Changed to String to hold status as a string value

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Many orders can belong to one consumer
    @JoinColumn(name = "consumerid", referencedColumnName = "consumerid", nullable = false) // Foreign key
    private ConsumerEntity consumer; // Relationship to ConsumerEntity

    @Column(name = "totalPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice; // Total price, must be non-negative

    // Default constructor
    public OrderEntity() {}

    // Constructor with parameters
    public OrderEntity(Timestamp datePlaced, String status, ConsumerEntity consumer, BigDecimal totalPrice) {
        this.datePlaced = datePlaced;
        this.status = status; // Accepting status as a String
        this.consumer = consumer;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Timestamp getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(Timestamp datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getStatus() {
        return status; // Returns status as a String
    }

    public void setStatus(String status) {
        this.status = status; // Accepting status as a String
    }

    public ConsumerEntity getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerEntity consumer) {
        this.consumer = consumer;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
