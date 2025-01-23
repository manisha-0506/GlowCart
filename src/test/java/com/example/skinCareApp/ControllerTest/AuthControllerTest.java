package com.example.skinCareApp.ControllerTest;

import com.example.skinCareApp.Controller.AuthController;
import com.example.skinCareApp.Controller.AuthRequest;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.utility.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void registerUser_ShouldReturnSuccess() throws Exception {
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.save(any(Users.class))).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));

        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void loginUser_ShouldReturnToken() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateToken(anyString(), any())).thenReturn("dummyToken");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("dummyToken"))
                .andReturn();

        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtils, times(1)).generateToken(anyString(), any());
    }
}
