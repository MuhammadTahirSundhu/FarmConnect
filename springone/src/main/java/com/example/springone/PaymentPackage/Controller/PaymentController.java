package com.example.springone.PaymentPackage.Controller;

import com.example.springone.PaymentPackage.Model.Payment;
import com.example.springone.PaymentPackage.Service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> insertPayment(@RequestBody Payment paymentModel) {
        try {
            log.debug("Inserting payment: {}", paymentModel); // Log the incoming payment data
            Payment savedPayment = paymentService.insertPayment(paymentModel);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while inserting payment", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable("id") int id) {
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while deleting payment with ID: " + id, e);
            return new ResponseEntity<>("Payment not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable("id") int id, @RequestBody Payment paymentModel) {
        try {
            paymentService.updatePayment(id, paymentModel);
            return new ResponseEntity<>("Payment updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while updating payment with ID: " + id, e);
            return new ResponseEntity<>("Error updating payment with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching payment with ID: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            if (payments == null || payments.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all payments", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable("id") int id) {
        try {
            Payment payment = paymentService.getPaymentByOrderId(id);
            if (payment == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching payment by Order ID: " + id, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
