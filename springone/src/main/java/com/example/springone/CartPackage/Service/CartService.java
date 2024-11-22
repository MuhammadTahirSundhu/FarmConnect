package com.example.springone.CartPackage.Service;


import com.example.springone.CartPackage.Model.Cart;

import java.util.List;

public interface CartService {

    Cart insertCart(Cart cart);

    void deleteCart(int id);

    Cart updateCart(int id, Cart cart);

    Cart getCartById(int id);

    List<Cart> getAllCarts();
}
