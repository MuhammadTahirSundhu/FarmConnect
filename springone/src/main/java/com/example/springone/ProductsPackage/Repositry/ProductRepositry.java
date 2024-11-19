package com.example.springone.ProductsPackage.Repositry;

import com.example.springone.ProductsPackage.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositry extends JpaRepository<ProductEntity,Integer> {
}
