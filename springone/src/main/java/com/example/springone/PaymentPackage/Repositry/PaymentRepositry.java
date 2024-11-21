package com.example.springone.PaymentPackage.Repositry;

import com.example.springone.PaymentPackage.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepositry extends JpaRepository<PaymentEntity,Integer> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.order.orderID = :orderId")
    Optional<PaymentEntity> findByOrder_OrderID(@Param("orderId") int orderId);


}
