package com.example.springone.CartPackage.Service;

import com.example.springone.CartPackage.Entity.CartEntity;
import com.example.springone.CartPackage.Entity.CartProductEntity;
import com.example.springone.CartPackage.Model.CartProduct;
import com.example.springone.CartPackage.Repositry.CartProductRepository;
import com.example.springone.CartPackage.Repositry.CartRepository;
import com.example.springone.ProductsPackage.Entity.ProductEntity;
import com.example.springone.ProductsPackage.Model.Product;
import com.example.springone.ProductsPackage.Repositry.ProductRepositry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartProductServiceImpl implements CartProductService {

    @Autowired
    private CartProductRepository cartProductRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private ProductRepositry productRepo;

    public CartProductServiceImpl(CartProductRepository cartProductRepo, CartRepository cartRepo) {
        this.cartProductRepo = cartProductRepo;
        this.cartRepo = cartRepo;
    }

    @Override
    public CartProduct insertCartProduct(CartProduct cartProduct) {
        try {
            CartProductEntity cartProductEntity = new CartProductEntity();

            // Validate and set Cart
            CartEntity cartEntity = cartRepo.findById(cartProduct.getCartId())
                    .orElseThrow(() -> new RuntimeException("Cart with ID " + cartProduct.getCartId() + " not found"));
            cartProductEntity.setCart(cartEntity);

            // Validate and set Product
            ProductEntity productEntity = productRepo.findById(cartProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product with ID " + cartProduct.getProductId() + " not found"));
            cartProductEntity.setProduct(productEntity);

            // Set Quantity
            cartProductEntity.setQuantity(cartProduct.getQuantity());

            // Save the entity
            CartProductEntity savedEntity = cartProductRepo.save(cartProductEntity);

            // Map back to CartProduct DTO
            cartProduct.setQuantity(savedEntity.getQuantity());
            log.info("CartProduct inserted successfully: {}", cartProduct);

            return cartProduct;
        } catch (Exception e) {
            log.error("Error inserting CartProduct: {}", e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void deleteCartProduct(int cartId, int productId) {
        try {
            Optional<CartProductEntity> optionalEntity = cartProductRepo.findByCartIdAndProductId(cartId, productId);
            if (optionalEntity.isPresent()) {
                cartProductRepo.delete(optionalEntity.get());
                log.info("Cart product deleted successfully. Cart ID: {}, Product ID: {}", cartId, productId);
            } else {
                throw new RuntimeException("Cart product with Cart ID " + cartId + " and Product ID " + productId + " not found.");
            }
        } catch (Exception e) {
            log.error("Error deleting cart product: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateCartProduct(int cartId, int productId, CartProductEntity cartProduct) {
        try {
            Optional<CartProductEntity> optionalEntity = cartProductRepo.findByCartIdAndProductId(cartId, productId);
            if (optionalEntity.isPresent()) {
                CartProductEntity existingEntity = optionalEntity.get();
                BeanUtils.copyProperties(cartProduct, existingEntity, "id", "cart");

                CartProductEntity updatedEntity = cartProductRepo.save(existingEntity);
                log.info("Cart product updated successfully: {}", updatedEntity);
            } else {
                throw new RuntimeException("Cart product with Cart ID " + cartId + " and Product ID " + productId + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating cart product: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public CartProductEntity getCartProductById(int cartId, int productId) {
        try {
            return cartProductRepo.findByCartIdAndProductId(cartId, productId)
                    .orElseThrow(() -> new RuntimeException("Cart product with Cart ID " + cartId + " and Product ID " + productId + " not found."));
        } catch (Exception e) {
            log.error("Error fetching cart product: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<CartProduct> getAllCartProducts() {
        try {
            List<CartProductEntity> entities = cartProductRepo.findAll();
            return entities.stream().map(entity -> {
                CartProduct cartProduct = new CartProduct();
                BeanUtils.copyProperties(entity, cartProduct);
                return cartProduct;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all cart products: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> getProductsByCartId(int id) {
        try {
            // Fetch all CartProductEntity by cartID
            List<CartProductEntity> cartProductEntities = cartProductRepo.findByCartId(id);

            // Map CartProductEntity to Product DTO
            return cartProductEntities.stream().map(cartProductEntity -> {
                Product product = new Product();
                ProductEntity productEntity = cartProductEntity.getProduct();

                // Copy properties from ProductEntity to Product DTO
                BeanUtils.copyProperties(productEntity, product);
                if (productEntity.getFarmer() != null) {
                    product.setFarmerID(productEntity.getFarmer().getFarmerid());
                }
                // Add quantity from CartProductEntity to Product DTO
                product.setStockQuantity(cartProductEntity.getQuantity());
                return product;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching products for cart ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
