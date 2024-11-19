package com.example.springone.ProductsPackage.Service;

import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Repositry.FarmerRepository;
import com.example.springone.ProductsPackage.Entity.ProductEntity;
import com.example.springone.ProductsPackage.Model.Product;
import com.example.springone.ProductsPackage.Repositry.ProductRepositry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private ProductRepositry productRepo;

    public ProductServiceImpl(ProductRepositry productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product insertProduct(Product product) {
        if (farmerRepository == null) {
            throw new IllegalStateException("FarmerRepository is not initialized");
        }
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(product, productEntity);

        FarmerEntity farmer = farmerRepository.findById(product.getFarmerID())
                .orElseThrow(() -> new RuntimeException("Farmer with ID " + product.getFarmerID() + " not found"));
        productEntity.setFarmer(farmer);
        productRepo.save(productEntity);
        return product;
    }

    @Override
    public void deleteProduct(int id) {
        Optional<ProductEntity> optionalProductEntity = productRepo.findById(id);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            productRepo.delete(productEntity);
            log.info("Product with ID " + id + " has been deleted successfully.");
        } else {
            throw new RuntimeException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public void updateProduct(int id, Product product) {
        try {
            Optional<ProductEntity> optionalProductEntity = productRepo.findById(id);
            if (optionalProductEntity.isPresent()) {
                ProductEntity productEntity = optionalProductEntity.get();
                productEntity.setName(product.getName());
                productEntity.setType(product.getType());
                productEntity.setPrice(product.getPrice());
                productEntity.setStockQuantity(product.getStockQuantity());

                productRepo.save(productEntity);
                log.info("Product with ID " + id + " updated successfully.");
            } else {
                throw new RuntimeException("Product with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating product: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Product getProductById(int id) {
        Optional<ProductEntity> optionalProductEntity = productRepo.findById(id);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            Product product = new Product();
            BeanUtils.copyProperties(productEntity, product);
            return product;
        } else {
            throw new RuntimeException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepo.findAll();

        return productEntities.stream()
                .map(productEntity -> {
                    Product product = new Product();
                    BeanUtils.copyProperties(productEntity, product);

                    // Manually map farmerId
                    if (productEntity.getFarmer() != null) {
                        product.setFarmerID(productEntity.getFarmer().getFarmerid());
                    }

                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByFarmerId(int id) {
        List<ProductEntity> productEntities = productRepo.findProductsByFarmerId(id);

        return productEntities.stream()
                .map(productEntity -> {
                    Product product = new Product();
                    BeanUtils.copyProperties(productEntity, product);

                    // Manually map farmerId
                    if (productEntity.getFarmer() != null) {
                        product.setFarmerID(productEntity.getFarmer().getFarmerid());
                    }

                    return product;
                })
                .collect(Collectors.toList());
    }

}
