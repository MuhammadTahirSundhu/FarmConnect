package com.example.springone.OrdersPackage.Controller;

import com.example.springone.OrdersPackage.Entity.OrderProductEntity;
import com.example.springone.OrdersPackage.Model.OrderProduct;
import com.example.springone.OrdersPackage.Service.OrderProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/order_products")
public class OrderProductController {

    private final OrderProductService orderProductService;

    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping
    public ResponseEntity<OrderProduct> createOrderProduct(@RequestBody OrderProduct orderProduct) {
        try {
            log.debug("Creating OrderProduct: {}", orderProduct);
            OrderProduct createdOrderProduct = orderProductService.createOrderProduct(orderProduct);
            return new ResponseEntity<>(createdOrderProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating OrderProduct", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable int orderId, @PathVariable int productId) {
        try {
            OrderProduct orderProduct = orderProductService.getOrderProductById(orderId, productId);
            if (orderProduct == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orderProduct, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching OrderProduct with ID: {}/{}", orderId, productId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        try {
            List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
            if (orderProducts == null || orderProducts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderProducts, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all OrderProducts", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}/{productId}")
    public ResponseEntity<String> updateOrderProduct(
            @PathVariable int orderId,
            @PathVariable int productId,
            @RequestBody OrderProduct orderProduct) {
        try {
            orderProductService.updateOrderProduct(orderId, productId, orderProduct);
            return new ResponseEntity<>("OrderProduct updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating OrderProduct with ID: {}/{}", orderId, productId, e);
            return new ResponseEntity<>("Error updating OrderProduct", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity<String> deleteOrderProduct(@PathVariable int orderId, @PathVariable int productId) {
        try {
            orderProductService.deleteOrderProduct(orderId, productId);
            return new ResponseEntity<>("OrderProduct deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting OrderProduct with ID: {}/{}", orderId, productId, e);
            return new ResponseEntity<>("OrderProduct not found", HttpStatus.NOT_FOUND);
        }
    }
}
