package com.example.skinCareApp.ServiceTest;

import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Entity.Payment;
import com.example.skinCareApp.Repository.PaymentRepository;
import com.example.skinCareApp.Service.OrderService;
import com.example.skinCareApp.Service.PaymentService;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testCreatePayment() throws StripeException {
        Order order = new Order();
        order.setTotalAmount(500L);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(500L);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        assertNotNull(paymentService.createPayment(order));
    }
}
