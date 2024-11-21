package com.example.springone.OrdersPackage.Repositry;

import com.example.springone.OrdersPackage.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("SELECT o FROM OrderEntity o WHERE o.consumer.consumerID = :consumerId") // Ensure consumerID matches the field in OrderEntity
    List<OrderEntity> findOrdersByConsumerId(@Param("consumerId") int consumerId); // Match 'consumerId' exactly with the query parameter
}
