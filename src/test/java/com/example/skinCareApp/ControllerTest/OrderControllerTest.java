package com.example.skinCareApp.ControllerTest;

import com.example.skinCareApp.Controller.OrderController;
import com.example.skinCareApp.Entity.Order;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testPlaceOrder() throws Exception {
        Users user = new Users();
        user.setId(100L);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotalAmount(250L); // Use Double for consistency

        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders/place-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(100L)) // Fix JSON path
                .andExpect(jsonPath("$.totalAmount").value(250L)); // Ensure correct type

        verify(orderService, times(1)).placeOrder(any(Order.class));
    }

    @Test
    void testGetOrderHistory() throws Exception {
        Users user = new Users();
        user.setId(100L);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setUser(user);
        order1.setTotalAmount(200L);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUser(user);
        order2.setTotalAmount(300L);

        List<Order> orderHistory = Arrays.asList(order1, order2);

        when(orderService.getOrderHistory(100L)).thenReturn(orderHistory);

        mockMvc.perform(get("/orders/history/{userId}", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[0].user.id").value(100L)) // Fix JSON path
                .andExpect(jsonPath("$[1].user.id").value(100L))
                .andExpect(jsonPath("$[0].totalAmount").value(200L))
                .andExpect(jsonPath("$[1].totalAmount").value(300L));

        verify(orderService, times(1)).getOrderHistory(100L);
    }

}
