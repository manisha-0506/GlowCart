package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Payment;
import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.PaymentMethodCreateParams.CardDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService; // Order service to fetch order details

    // Create a Stripe Payment Intent
    public Payment createPayment(Order order) throws StripeException {
        long totalAmountInCents = order.getTotalAmount(); // Order amount in cents

        // Test Card (hard-coded for testing purposes)
        String testCardNumber = "4242424242424242";
        Long testCardExpiryMonth = 12L;
        String testCardExpiryYear = "2025";
        String testCardCvc = "123";

        // Create a PaymentMethod using test card details
        PaymentMethodCreateParams.CardDetails cardParams = PaymentMethodCreateParams.CardDetails.builder()
                .setNumber(testCardNumber)
                .setExpMonth(testCardExpiryMonth)
                .setExpYear(Long.valueOf(testCardExpiryYear))
                .setCvc(testCardCvc)
                .build();

        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
                .setType(PaymentMethodCreateParams.Type.CARD)
                .setCard(cardParams)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

        // Create a PaymentIntent with the PaymentMethod ID
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(totalAmountInCents)
                .setCurrency("usd")
                .setPaymentMethod(paymentMethod.getId()) // Use the created PaymentMethod
                .setConfirm(true) // Automatically confirm the payment
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Create and save payment record
        Payment payment = new Payment();
        payment.setPaymentMethod("Stripe");
        payment.setPaymentStatus("PENDING");
        payment.setPaymentIntentId(paymentIntent.getId());
        payment.setAmount(totalAmountInCents);
        payment.setOrder(order);

        return paymentRepository.save(payment); // Save the payment to the database
    }

    // Confirm the payment after it has been processed
    public Payment confirmPayment(Long paymentId) throws StripeException {
        // Retrieve the payment from the database
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Retrieve the payment intent from Stripe
        PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentIntentId());

        // Confirm payment intent status
        if ("succeeded".equals(paymentIntent.getStatus())) {
            payment.setPaymentStatus("COMPLETED");
            // Update the order status to "COMPLETED" (or similar)
        } else {
            payment.setPaymentStatus("FAILED");
        }

        return paymentRepository.save(payment); // Save the updated payment status
    }
}
