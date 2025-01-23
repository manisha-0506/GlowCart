package com.example.skinCareApp.Entity;

import com.example.skinCareApp.Entity.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private com.example.skinCareApp.Entity.Order order; // The order this item belongs to

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // The product ordered

    private int quantity; // The quantity of the product ordered

    private Long price; // Price of the product in cents (e.g., 1000 = $10.00)
}
