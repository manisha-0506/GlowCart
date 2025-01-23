package com.example.skinCareApp.ControllerTest;

import com.example.skinCareApp.Controller.CartController;
import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void testCreateCart() throws Exception {
        Cart cart = new Cart();
        cart.setcartId(1L);
        cart.setUserId(1L);

        when(cartService.createCartForUser(1L)).thenReturn(cart);

        mockMvc.perform(post("/carts/create")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L)); // Use cartId instead of id

        verify(cartService, times(1)).createCartForUser(1L);
    }

    @Test
    void testAddProductToCart() throws Exception {
        Cart cart = new Cart();
        cart.setcartId(1L);

        when(cartService.addProductToCart(1L, 2L)).thenReturn(cart);

        mockMvc.perform(put("/carts/1/addProduct")
                        .param("productId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L)); // Use cartId instead of id

        verify(cartService, times(1)).addProductToCart(1L, 2L);
    }

    @Test
    void testViewCart() throws Exception {
        Cart cart = new Cart();
        cart.setcartId(1L);

        when(cartService.viewCart(1L)).thenReturn(cart);

        mockMvc.perform(get("/carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1L)); // Use cartId instead of id

        verify(cartService, times(1)).viewCart(1L);
    }
}
