package com.example.skinCareApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod; // e.g., "Stripe"

    private String paymentStatus; // e.g., "SUCCESS", "FAILED"

    private String paymentIntentId; // The payment intent ID (from Stripe)

    private Long amount; // Amount paid in cents

    @OneToOne(mappedBy = "payment")
    private com.example.skinCareApp.Entity.Order order; // Order related to this payment
}
