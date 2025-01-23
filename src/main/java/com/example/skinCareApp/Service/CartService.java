package com.example.skinCareApp.Service;
import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Entity.Product;

public interface CartService {
    Cart createCartForUser(Long userId);
    Cart addProductToCart(Long cartId, Long productId);
    Cart viewCart(Long cartId);
}

