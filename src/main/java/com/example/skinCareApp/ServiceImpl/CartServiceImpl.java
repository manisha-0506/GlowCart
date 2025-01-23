package com.example.skinCareApp.ServiceImpl;

import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.CartRepository;
import com.example.skinCareApp.Repository.ProductRepository;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart createCartForUser(Long userId) {
        // Check if the user exists
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user already has a cart
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            throw new RuntimeException("Cart already exists for this user");
        }

        // Create a new cart
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public Cart addProductToCart(Long cartId, Long productId) {
        // Find the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Add the product to the cart
        cart.getProducts().add(product);

        return cartRepository.save(cart);
    }

    @Override
    public Cart viewCart(Long cartId) {
        // Find and return the cart
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}
