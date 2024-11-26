package com.example.springone.CartPackage.Service;

import com.example.springone.CartPackage.Entity.CartProductEntity;
import com.example.springone.CartPackage.Model.CartProduct;
import com.example.springone.ProductsPackage.Model.Product;

import java.util.List;

public interface CartProductService {
    CartProduct insertCartProduct(CartProduct cartProduct);

    void deleteCartProduct(int cartId, int productId);

    void updateCartProduct(int cartId, int productId, CartProductEntity cartProduct);

    CartProductEntity getCartProductById(int cartId, int productId);

    List<CartProduct> getAllCartProducts();

    List<Product> getProductsByCartId(int id);

}
