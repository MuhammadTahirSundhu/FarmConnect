package com.example.springone.CartPackage.Service;

import com.example.springone.CartPackage.Entity.CartEntity;
import com.example.springone.CartPackage.Model.Cart;
import com.example.springone.CartPackage.Repositry.CartRepository;
import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.ConsumerPackage.Repositry.ConsumerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private ConsumerRepository consumerRepo;

    public CartServiceImpl(CartRepository cartRepo, ConsumerRepository consumertRepo) {
        this.cartRepo = cartRepo;
        this.consumerRepo = consumertRepo;
    }

    @Override
    public Cart insertCart(Cart cart) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cart, cartEntity);

        // Ensure consumer exists and set it
        ConsumerEntity consumer = consumerRepo.findById(cart.getConsumerID())
                .orElseThrow(() -> new RuntimeException("Consumer with ID " + cart.getConsumerID() + " not found"));
        cartEntity.setConsumer(consumer);

        // Save cartEntity
        CartEntity savedCartEntity = cartRepo.save(cartEntity);

        // Assign the generated ID back to the Cart object
        cart.setCartID(savedCartEntity.getCartID());
        log.info("Cart inserted successfully: {}", cart);

        return cart;
    }

    @Override
    public void deleteCart(int id) {
        try {
            Optional<CartEntity> optionalCartEntity = cartRepo.findById(id);
            if (optionalCartEntity.isPresent()) {
                cartRepo.delete(optionalCartEntity.get());
                log.info("Cart with ID {} deleted successfully.", id);
            } else {
                throw new RuntimeException("Cart with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error deleting cart: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Cart updateCart(int id, Cart cart) {
        try {
            Optional<CartEntity> optionalCartEntity = cartRepo.findById(id);
            if (optionalCartEntity.isPresent()) {
                CartEntity cartEntity = optionalCartEntity.get();
                BeanUtils.copyProperties(cart, cartEntity, "cartID");

                    cartEntity.setConsumer(consumerRepo.findById(cart.getConsumerID())
                            .orElseThrow(() -> new RuntimeException("Consumer with ID " + cart.getConsumerID() + " not found")));

                CartEntity updatedCartEntity = cartRepo.save(cartEntity);

                Cart updatedCart = new Cart();
                BeanUtils.copyProperties(updatedCartEntity, updatedCart);

                log.info("Cart with ID {} updated successfully.", id);

                return updatedCart;
            } else {
                throw new RuntimeException("Cart with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating cart: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public Cart getCartById(int id) {
        try {
            // Fetch the CartEntity by ID
            Optional<CartEntity> optionalCartEntity = cartRepo.findById(id);

            // Check if CartEntity is present
            if (optionalCartEntity.isPresent()) {
                CartEntity cartEntity = optionalCartEntity.get();
                Cart cart = new Cart();
                BeanUtils.copyProperties(cartEntity, cart);

                // Check if the Consumer object is not null
                if (cartEntity.getConsumer() != null) {
                    cart.setConsumerID(cartEntity.getConsumer().getConsumerID());
                } else {
                    cart.setConsumerID(0); // Default value or error value
                }

                log.info("Fetched cart with ID {}: {}", id, cart);
                return cart;
            } else {
                throw new RuntimeException("Cart with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error fetching cart: {}", e.getMessage(), e);
            throw e; // Optionally rethrow or handle the exception
        }
    }


    @Override
    public List<Cart> getAllCarts() {
        try {
            // Fetch all CartEntities from the repository
            List<CartEntity> cartEntities = cartRepo.findAll();
            log.info("Fetched all carts: {}", cartEntities);

            // Map CartEntity objects to Cart model objects and return the list
            return cartEntities.stream()
                    .map(cartEntity -> {
                        Cart cart = new Cart();
                        BeanUtils.copyProperties(cartEntity, cart);

                        // Check if the Consumer object is not null and retrieve the consumerID
                        if (cartEntity.getConsumer() != null && cartEntity.getConsumer().getConsumerID() > 0) {
                            cart.setConsumerID(cartEntity.getConsumer().getConsumerID());
                        } else {
                            cart.setConsumerID(0); // Default or error value if no valid consumerID
                        }
                        return cart;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all carts: {}", e.getMessage(), e);
            // Optionally, rethrow the exception or return an empty list depending on your needs
            throw e;
        }
    }

}
