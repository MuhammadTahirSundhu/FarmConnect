package com.example.springone.OrdersPackage.Service;

import com.example.springone.OrdersPackage.Model.OrderProduct;

import java.util.List;

public interface OrderProductService {
    OrderProduct createOrderProduct(OrderProduct orderProduct);

    OrderProduct getOrderProductById(int orderId, int productId);

    List<OrderProduct> getAllOrderProducts();

    OrderProduct updateOrderProduct(int orderId, int productId, OrderProduct orderProduct);

    void deleteOrderProduct(int orderId, int productId);
}
