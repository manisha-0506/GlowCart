package com.example.skinCareApp.ServiceTest;

import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.CartRepository;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.ServiceImpl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Users user;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("testUser");

        cart = new Cart();
        cart.setcartId(1L);
        cart.setUser(user);
    }

    @Test
    void testCreateCartForUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartService.createCartForUser(1L);

        assertNotNull(createdCart);
        assertEquals(1L, createdCart.getUser().getId());

        verify(userRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testCreateCartForUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> cartService.createCartForUser(1L));

        assertEquals("User not found", exception.getMessage());
    }
}
