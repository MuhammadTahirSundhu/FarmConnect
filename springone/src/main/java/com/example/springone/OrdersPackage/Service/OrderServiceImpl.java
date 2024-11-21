package com.example.springone.OrdersPackage.Service;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.ConsumerPackage.Repositry.ConsumerRepository;
import com.example.springone.OrdersPackage.Entity.OrderEntity;
import com.example.springone.OrdersPackage.Model.Order;
import com.example.springone.OrdersPackage.Repositry.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private OrderRepository orderRepo;

    public OrderServiceImpl(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order createOrder(Order order) {
        if (consumerRepository == null) {
            throw new IllegalStateException("ConsumerRepository is not initialized");
        }

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);

        ConsumerEntity consumer = consumerRepository.findById(order.getConsumerID())
                .orElseThrow(() -> new RuntimeException("Consumer with ID " + order.getConsumerID() + " not found"));
        orderEntity.setConsumer(consumer);
        orderRepo.save(orderEntity);
        return order;
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepo.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            Order order = new Order();
            BeanUtils.copyProperties(orderEntity, order);
            return order;
        } else {
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepo.findAll();

        return orderEntities.stream()
                .map(orderEntity -> {
                    Order order = new Order();
                    BeanUtils.copyProperties(orderEntity, order);

                    // Manually map consumerId
                    if (orderEntity.getConsumer() != null) {
                        order.setConsumerID(orderEntity.getConsumer().getConsumerID());
                    }

                    return order;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByConsumerId(int consumerId) {
        List<OrderEntity> orderEntities = orderRepo.findOrdersByConsumerId(consumerId);

        return orderEntities.stream()
                .map(orderEntity -> {
                    Order order = new Order();
                    BeanUtils.copyProperties(orderEntity, order);

                    // Manually map consumerId
                    if (orderEntity.getConsumer() != null) {
                        order.setConsumerID(orderEntity.getConsumer().getConsumerID());
                    }

                    return order;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrder(int orderId, Order order) {
        try {
            Optional<OrderEntity> optionalOrderEntity = orderRepo.findById(orderId);
            if (optionalOrderEntity.isPresent()) {
                OrderEntity orderEntity = optionalOrderEntity.get();
                orderEntity.setStatus(order.getStatus());
                orderEntity.setTotalPrice(order.getTotalPrice());

                orderRepo.save(orderEntity);
                log.info("Order with ID " + orderId + " updated successfully.");
            } else {
                throw new RuntimeException("Order with ID " + orderId + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating order: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepo.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            orderRepo.delete(orderEntity);
            log.info("Order with ID " + orderId + " has been deleted successfully.");
        } else {
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }
    }
}
