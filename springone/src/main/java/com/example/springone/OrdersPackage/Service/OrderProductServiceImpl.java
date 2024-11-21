package com.example.springone.OrdersPackage.Service;

import com.example.springone.OrdersPackage.Entity.OrderEntity;
import com.example.springone.OrdersPackage.Entity.OrderProductEntity;
import com.example.springone.OrdersPackage.HelperClasses.OrderProductId;
import com.example.springone.OrdersPackage.Model.OrderProduct;
import com.example.springone.OrdersPackage.Repositry.OrderProductRepositry;
import com.example.springone.OrdersPackage.Repositry.OrderRepository;
import com.example.springone.ProductsPackage.Entity.ProductEntity;
import com.example.springone.ProductsPackage.Repositry.ProductRepositry;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepositry orderProductRepository;

    @Autowired
    private ProductRepositry productRepository;

    // Create OrderProduct
    @Override
    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        try {
            // Find the associated Order
            Optional<OrderEntity> orderOptional = orderRepository.findById(orderProduct.getOrderID());
            if (!orderOptional.isPresent()) {
                throw new RuntimeException("Order with ID " + orderProduct.getOrderID() + " not found");
            }

            // Find the associated Product
            Optional<ProductEntity> productOptional = productRepository.findById(orderProduct.getProductID());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product with ID " + orderProduct.getProductID() + " not found");
            }

            // Create OrderProductEntity and save it
            OrderProductEntity orderProductEntity = new OrderProductEntity();
            OrderProductId opi = new OrderProductId();
            opi.setProductID(orderProduct.getProductID());
            opi.setOrderID(orderProduct.getOrderID());
            orderProductEntity.setId(opi);
            orderProductEntity.setOrder(orderOptional.get());
            orderProductEntity.setProduct(productOptional.get());
            orderProductEntity.setQuantity(orderProduct.getQuantity());

            orderProductRepository.save(orderProductEntity);

            log.info("Created OrderProduct for Order ID: " + orderProduct.getOrderID());
            return orderProduct;
        } catch (Exception e) {
            log.error("Error creating OrderProduct: ", e);
            throw new RuntimeException("Error creating OrderProduct", e);
        }
    }

    // Get OrderProduct by OrderID and ProductID
    @Override
    public OrderProduct getOrderProductById(int orderID, int productID) {
        try {
            Optional<OrderProductEntity> optionalOrderProductEntity = orderProductRepository.findByOrderIdAndProductId(orderID, productID);
            if (!optionalOrderProductEntity.isPresent()) {
                throw new RuntimeException("OrderProduct with Order ID " + orderID + " and Product ID " + productID + " not found.");
            }

            // Convert OrderProductEntity to OrderProduct Model
            OrderProductEntity orderProductEntity = optionalOrderProductEntity.get();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderID(orderProductEntity.getOrder().getOrderID());
            orderProduct.setProductID(orderProductEntity.getProduct().getProductID());
            orderProduct.setQuantity(orderProductEntity.getQuantity());

            return orderProduct;
        } catch (Exception e) {
            log.error("Error fetching OrderProduct: ", e);
            throw new RuntimeException("Error fetching OrderProduct", e);
        }
    }

    // Get all OrderProducts
    @Override
    public List<OrderProduct> getAllOrderProducts() {
        try {
            List<OrderProductEntity> orderProductEntities = orderProductRepository.findAll();

            return orderProductEntities.stream().map(entity -> {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderID(entity.getOrder().getOrderID());
                orderProduct.setProductID(entity.getProduct().getProductID());
                orderProduct.setQuantity(entity.getQuantity());
                return orderProduct;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all OrderProducts: ", e);
            throw new RuntimeException("Error fetching all OrderProducts", e);
        }
    }

    // Update OrderProduct
    @Override
    public OrderProduct updateOrderProduct(int orderID, int productID, OrderProduct orderProduct) {
        try {
            Optional<OrderProductEntity> optionalOrderProductEntity = orderProductRepository.findByOrderIdAndProductId(orderID, productID);
            if (!optionalOrderProductEntity.isPresent()) {
                throw new RuntimeException("OrderProduct with Order ID " + orderID + " and Product ID " + productID + " not found.");
            }

            OrderProductEntity orderProductEntity = optionalOrderProductEntity.get();
            orderProductEntity.setQuantity(orderProduct.getQuantity());

            orderProductRepository.save(orderProductEntity);

            log.info("Updated OrderProduct for Order ID: " + orderID + " and Product ID: " + productID);
            return orderProduct;
        } catch (Exception e) {
            log.error("Error updating OrderProduct: ", e);
            throw new RuntimeException("Error updating OrderProduct", e);
        }
    }

    // Delete OrderProduct
    @Override
    public void deleteOrderProduct(int orderID, int productID) {
        try {
            Optional<OrderProductEntity> optionalOrderProductEntity = orderProductRepository.findByOrderIdAndProductId(orderID, productID);
            if (!optionalOrderProductEntity.isPresent()) {
                throw new RuntimeException("OrderProduct with Order ID " + orderID + " and Product ID " + productID + " not found.");
            }

            orderProductRepository.delete(optionalOrderProductEntity.get());
            log.info("Deleted OrderProduct for Order ID: " + orderID + " and Product ID: " + productID);
        } catch (Exception e) {
            log.error("Error deleting OrderProduct: ", e);
            throw new RuntimeException("Error deleting OrderProduct", e);
        }
    }
}
