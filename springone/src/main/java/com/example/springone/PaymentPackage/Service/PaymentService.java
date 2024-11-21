package com.example.springone.PaymentPackage.Service;

import com.example.springone.PaymentPackage.Model.Payment;

import java.util.List;

public interface PaymentService {

    Payment insertPayment(Payment paymentModel);

    void deletePayment(int id);

    void updatePayment(int id, Payment paymentModel);

    Payment getPaymentById(int id);

    List<Payment> getAllPayments();

    Payment getPaymentByOrderId(int id);
}
