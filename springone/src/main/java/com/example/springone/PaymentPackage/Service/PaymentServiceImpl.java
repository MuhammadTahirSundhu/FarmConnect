package com.example.springone.PaymentPackage.Service;

import com.example.springone.OrdersPackage.Entity.OrderEntity;
import com.example.springone.OrdersPackage.Repositry.OrderRepository;
import com.example.springone.PaymentPackage.Entity.PaymentEntity;
import com.example.springone.PaymentPackage.Model.Payment;
import com.example.springone.PaymentPackage.Repositry.PaymentRepositry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepositry paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment insertPayment(Payment paymentModel) {
        PaymentEntity paymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(paymentModel, paymentEntity);

        // Fetch the associated OrderEntity
        OrderEntity order = orderRepository.findById(paymentModel.getOrderID())
                .orElseThrow(() -> new RuntimeException("Order with ID " + paymentModel.getOrderID() + " not found"));
        paymentEntity.setOrder(order);

        paymentRepository.save(paymentEntity);
        log.info("Payment has been inserted successfully: {}", paymentEntity);
        return paymentModel;
    }

    @Override
    public void deletePayment(int id) {
        Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
        if (optionalPaymentEntity.isPresent()) {
            PaymentEntity paymentEntity = optionalPaymentEntity.get();
            paymentRepository.delete(paymentEntity);
            log.info("Payment with ID {} has been deleted successfully.", id);
        } else {
            throw new RuntimeException("Payment with ID " + id + " not found.");
        }
    }

    @Override
    public void updatePayment(int id, Payment paymentModel) {
        Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
        if (optionalPaymentEntity.isPresent()) {
            PaymentEntity paymentEntity = optionalPaymentEntity.get();
            paymentEntity.setAmount(paymentModel.getAmount());
            paymentEntity.setMethod(paymentModel.getMethod());
            paymentEntity.setStatus(paymentModel.getStatus());

            // Update the associated order if necessary
            OrderEntity order = orderRepository.findById(paymentModel.getOrderID())
                    .orElseThrow(() -> new RuntimeException("Order with ID " + paymentModel.getOrderID() + " not found"));
            paymentEntity.setOrder(order);

            paymentRepository.save(paymentEntity);
            log.info("Payment with ID {} has been updated successfully.", id);
        } else {
            throw new RuntimeException("Payment with ID " + id + " not found.");
        }
    }

    @Override
    public Payment getPaymentById(int id) {
        Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
        if (optionalPaymentEntity.isPresent()) {
            PaymentEntity paymentEntity = optionalPaymentEntity.get();
            Payment paymentModel = new Payment();
            BeanUtils.copyProperties(paymentEntity, paymentModel);

            // Set the order ID explicitly
            if (paymentEntity.getOrder() != null) {
                paymentModel.setOrderID(paymentEntity.getOrder().getOrderID());
            }

            return paymentModel;
        } else {
            throw new RuntimeException("Payment with ID " + id + " not found.");
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        List<PaymentEntity> paymentEntities = paymentRepository.findAll();
        return paymentEntities.stream()
                .map(paymentEntity -> {
                    Payment paymentModel = new Payment();
                    BeanUtils.copyProperties(paymentEntity, paymentModel);

                    // Map the order ID explicitly
                    if (paymentEntity.getOrder() != null) {
                        paymentModel.setOrderID(paymentEntity.getOrder().getOrderID());
                    }

                    return paymentModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Payment getPaymentByOrderId(int id) {
        Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findByOrder_OrderID(id);
        if (optionalPaymentEntity.isPresent()) {
            PaymentEntity paymentEntity = optionalPaymentEntity.get();
            Payment paymentModel = new Payment();
            BeanUtils.copyProperties(paymentEntity, paymentModel);

            // Set the order ID explicitly
            paymentModel.setOrderID(paymentEntity.getOrder().getOrderID());
            return paymentModel;
        } else {
            throw new RuntimeException("Payment for Order ID " + id + " not found.");
        }
    }
}
