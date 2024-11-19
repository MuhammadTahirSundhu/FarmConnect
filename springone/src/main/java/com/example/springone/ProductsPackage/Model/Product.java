package com.example.springone.ProductsPackage.Model;

import java.math.BigDecimal;

public class Product {

    private int productID;
    private String name;
    private String type;
    private BigDecimal price;
    private int stockQuantity;
    private int farmerID;  // Using farmerID for transferring only the ID, not the entire farmer entity.

    // Default constructor
    public Product() {}

    // Constructor with parameters
    public Product(int productID, String name, String type, BigDecimal price, int stockQuantity, int farmerID) {
        this.productID = productID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.farmerID = farmerID;
    }

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(int farmerID) {
        this.farmerID = farmerID;
    }
}
