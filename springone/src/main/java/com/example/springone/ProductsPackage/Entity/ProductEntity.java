package com.example.springone.ProductsPackage.Entity;

import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Product")  // Map to the Product table
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment primary key
    @Column(name = "productID")  // Match column name from the schema
    private int productID;

    @Column(name = "name", nullable = false)  // Name is mandatory, can't be null
    private String name;

    @Column(name = "type")  // Type of the product, optional
    private String type;

    @Column(name = "price", nullable = false)  // Price can't be null, must be positive
    private BigDecimal price;

    @Column(name = "stockQuantity", nullable = false)  // Stock quantity can't be negative
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)  // Many products can belong to one farmer
    @JoinColumn(name = "farmerID", referencedColumnName = "farmerID", nullable = false)  // Foreign key
    private FarmerEntity farmer;

    // Default constructor
    public ProductEntity() {}

    // Constructor with parameters
    public ProductEntity(String name, String type, BigDecimal price, int stockQuantity, FarmerEntity farmer) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.farmer = farmer;
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

    public FarmerEntity getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerEntity farmer) {
        this.farmer = farmer;
    }
}
