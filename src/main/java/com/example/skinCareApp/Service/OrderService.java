package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Entity.OrderItem;
import com.example.skinCareApp.Entity.Payment;
import com.example.skinCareApp.Repository.OrderRepository;
import com.example.skinCareApp.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Order placeOrder(Order order) {
        // Calculate total amount and set status
        long totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");

        // Save order to DB
        Order savedOrder = orderRepository.save(order);

        // Payment processing should be triggered here (e.g., create a PaymentIntent via Stripe)

        // Create Payment
        Payment payment = new Payment();
        payment.setPaymentMethod("Stripe");
        payment.setPaymentStatus("PENDING");
        payment.setAmount(totalAmount);
        payment.setOrder(savedOrder);
        paymentRepository.save(payment);

        return savedOrder;
    }

    public List<Order> getOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId); // Fetch orders by user ID
    }
}
