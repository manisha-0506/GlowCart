package com.example.skinCareApp.ServiceTest;

import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.Service.CustomerUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUserDetailsServiceTest {

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private CustomerUserDetailsService userDetailsService;

    @Test
    void testLoadUserByUsername_UserExists() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole("USER");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        assertNotNull(userDetailsService.loadUserByUsername("testUser"));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("unknown"));

        assertEquals("User not found", exception.getMessage());
    }
}
