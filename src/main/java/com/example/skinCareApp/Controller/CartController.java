package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
        @Autowired
        private CartService cartService;

        @PostMapping("/create")
        public Cart createCart(@RequestParam Long userId) {
            return cartService.createCartForUser(userId);
        }

        @PutMapping("/{cartId}/addProduct")
        public Cart addProductToCart(@PathVariable Long cartId, @RequestParam Long productId) {
            return cartService.addProductToCart(cartId, productId);
        }

        @GetMapping("/{cartId}")
        public Cart viewCart(@PathVariable Long cartId) {
            return cartService.viewCart(cartId);
        }


}
