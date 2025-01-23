package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Entity.Payment;
import com.example.skinCareApp.Service.PaymentService;
import com.example.skinCareApp.Service.OrderService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    // Endpoint to create a payment for an order
    @PostMapping("/create-payment")
    public Payment createPayment(@RequestBody Order order) throws StripeException {
        return paymentService.createPayment(order); // Call payment service to create payment
    }

    // Endpoint to confirm payment after Stripe has processed it
    @PostMapping("/confirm-payment/{paymentId}")
    public Payment confirmPayment(@PathVariable Long paymentId) throws StripeException {
        // Logic to confirm payment and update status
        Payment payment = paymentService.confirmPayment(paymentId);
        return payment;
    }
}
