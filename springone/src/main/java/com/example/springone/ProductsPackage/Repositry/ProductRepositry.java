package com.example.springone.ProductsPackage.Repositry;

import com.example.springone.ProductsPackage.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepositry extends JpaRepository<ProductEntity,Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE p.farmer.farmerid = :farmerid")
    List<ProductEntity> findProductsByFarmerId(@Param("farmerid") int farmerid);

}
