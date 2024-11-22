package com.example.springone.CartPackage.Controller;

import com.example.springone.CartPackage.Model.Cart;
import com.example.springone.CartPackage.Service.CartService;
import com.example.springone.DTO.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> insertCart(@RequestBody Cart cart) {
        try {
            Cart savedCart = cartService.insertCart(cart);
            return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error inserting cart: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") int id) {
        try {
            cartService.deleteCart(id);
            return new ResponseEntity<>("Cart deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting cart with ID: " + id, e);
            return new ResponseEntity<>("Cart not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<String> updateCart(@PathVariable("id") int id, @RequestBody Cart cart) {
        try {
            cartService.updateCart(id, cart);
            return new ResponseEntity<>("Cart updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating cart with ID: " + id, e);
            return new ResponseEntity<>("Error updating cart with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") int id) {
        try {
            Cart cart = cartService.getCartById(id);
            if (cart == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching cart with ID: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getAllCarts() {
        try {
            List<Cart> carts = cartService.getAllCarts();
            if (carts == null || carts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching carts: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}