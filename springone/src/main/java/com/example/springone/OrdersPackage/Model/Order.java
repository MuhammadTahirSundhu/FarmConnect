package com.example.springone.OrdersPackage.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private int orderID; // Unique identifier for the order
    private Date datePlaced; // Date the order was placed
    private String status; // Status of the order (as a String)
    private int consumerID; // ID of the consumer who placed the order
    private BigDecimal totalPrice; // Total price of the order

    // Default constructor
    public Order() {}

    // Constructor with parameters
    public Order(int orderID, Date datePlaced, String status, int consumerID, BigDecimal totalPrice) {
        this.orderID = orderID;
        this.datePlaced = datePlaced;
        this.status = status;
        this.consumerID = consumerID;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", datePlaced=" + datePlaced +
                ", status='" + status + '\'' +
                ", consumerID=" + consumerID +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
