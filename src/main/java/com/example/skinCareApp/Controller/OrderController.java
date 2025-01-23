package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/history/{userId}")
    public List<Order> getOrderHistory(@PathVariable Long userId) {
        return orderService.getOrderHistory(userId);
    }
}
