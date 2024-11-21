package com.example.springone.OrdersPackage.Service;

import com.example.springone.OrdersPackage.Model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByConsumerId(int consumerId);

    void updateOrder(int orderId, Order order);

    void deleteOrder(int orderId);
}
