package com.example.skinCareApp.Entity;

import com.example.skinCareApp.Entity.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user; // The user who placed the order

    private String status; // e.g., "PENDING", "COMPLETED", "CANCELLED"

    private Long totalAmount; // Total price of all items in cents (e.g., 1000 = $10.00)

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems; // List of products in the order

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment; // Payment details related to this order

    public void setUserId(long l) {
        user.setId(l);
    }
}
