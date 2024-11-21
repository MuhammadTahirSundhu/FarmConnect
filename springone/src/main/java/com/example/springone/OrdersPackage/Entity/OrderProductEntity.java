package com.example.springone.OrdersPackage.Entity;

import com.example.springone.OrdersPackage.HelperClasses.OrderProductId;
import com.example.springone.ProductsPackage.Entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order_Product")
public class OrderProductEntity {

    // Use @EmbeddedId for composite keys
    @EmbeddedId
    private OrderProductId id;

    // Many-to-one relationship with OrderEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderID")
    @JoinColumn(name = "orderID", insertable = false, updatable = false, nullable = false)
    private OrderEntity order;

    // Many-to-one relationship with ProductEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productID")
    @JoinColumn(name = "productID", insertable = false, updatable = false, nullable = false)
    private ProductEntity product;

    // Quantity of the product in the order
    @Column(nullable = false)
    private int quantity;

    public OrderProductId getId() {
        return id;
    }

    public void setId(OrderProductId id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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
