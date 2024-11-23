package com.example.springone.CartPackage.Repositry;

import com.example.springone.CartPackage.Entity.CartProductEntity;
import com.example.springone.CartPackage.HelperClasses.CartProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProductEntity, CartProductId> {
    @Query("SELECT cp FROM CartProductEntity cp WHERE cp.cart.cartID = :cartId AND cp.product.productID = :productId")
    Optional<CartProductEntity> findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

}
