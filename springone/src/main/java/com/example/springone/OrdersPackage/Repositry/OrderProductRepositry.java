package com.example.springone.OrdersPackage.Repositry;

import com.example.springone.OrdersPackage.Entity.OrderProductEntity;
import com.example.springone.OrdersPackage.HelperClasses.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderProductRepositry extends JpaRepository<OrderProductEntity, OrderProductId> {
    @Query("SELECT op FROM OrderProductEntity op WHERE op.id.orderID = :orderID AND op.id.productID = :productID")
    Optional<OrderProductEntity> findByOrderIdAndProductId(int orderID, int productID);
}
