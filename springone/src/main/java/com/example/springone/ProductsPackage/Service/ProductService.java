package com.example.springone.ProductsPackage.Service;

import com.example.springone.ProductsPackage.Model.Product;

import java.util.List;

public interface ProductService {
    Product insertProduct(Product productModel);

    void deleteProduct(int id);

    void updateProduct(int id, Product productModel);

    Product getProductById(int id);

    List<Product> getAllProducts();
}
