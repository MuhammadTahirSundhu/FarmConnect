package com.example.springone.OrdersPackage.Controller;

import com.example.springone.OrdersPackage.Model.Order;
import com.example.springone.OrdersPackage.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            log.debug("Creating order: {}", order);
            Order createdOrder = orderService.createOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating order", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching order with ID: {}", orderId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            if (orders == null || orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all orders", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consumer/{consumerId}")
    public ResponseEntity<List<Order>> getOrdersByConsumerId(@PathVariable int consumerId) {
        try {
            List<Order> orders = orderService.getOrdersByConsumerId(consumerId);
            if (orders == null || orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching orders by consumer ID: {}", consumerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
        try {
            orderService.updateOrder(orderId, order);
            return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating order with ID: {}", orderId, e);
            return new ResponseEntity<>("Error updating order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting order with ID: {}", orderId, e);
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
