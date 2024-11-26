package com.example.springone.CartPackage.Controller;

import com.example.springone.CartPackage.Entity.CartProductEntity;
import com.example.springone.CartPackage.Model.CartProduct;
import com.example.springone.CartPackage.Service.CartProductService;
import com.example.springone.ProductsPackage.Model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/CartProduct")
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<CartProduct> insertCartProduct(@RequestBody CartProduct cartProduct) {
        try {
            CartProduct savedCartProduct = cartProductService.insertCartProduct(cartProduct);
            return new ResponseEntity<>(savedCartProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error inserting cart product: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<String> deleteCartProduct(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId) {
        try {
            cartProductService.deleteCartProduct(cartId, productId);
            return new ResponseEntity<>("Cart product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting cart product with Cart ID: " + cartId + " and Product ID: " + productId, e);
            return new ResponseEntity<>("Cart product not found with given IDs", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<String> updateCartProduct(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId, @RequestBody CartProductEntity cartProduct) {
        try {
            cartProductService.updateCartProduct(cartId, productId, cartProduct);
            return new ResponseEntity<>("Cart product updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating cart product with Cart ID: " + cartId + " and Product ID: " + productId, e);
            return new ResponseEntity<>("Error updating cart product with given IDs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cartId}/{productId}")
    public ResponseEntity<CartProductEntity> getCartProductById(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId) {
        try {
            CartProductEntity cartProduct = cartProductService.getCartProductById(cartId, productId);
            if (cartProduct == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cartProduct, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching cart product with Cart ID: " + cartId + " and Product ID: " + productId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CartProduct>> getAllCartProducts() {
        try {
            List<CartProduct> cartProducts = cartProductService.getAllCartProducts();
            if (cartProducts == null || cartProducts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cartProducts, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching cart products: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cart/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCartId(@PathVariable("id") int id) {
        try {
            List<Product> products = cartProductService.getProductsByCartId(id);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching products for cart ID: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
