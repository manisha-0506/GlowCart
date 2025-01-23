package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Users;

import java.util.Optional;

public interface UsersService {

    Users registerUser(Users user);

    Users findUserByUsername(String username);
    Optional<Users> findById(Long id);

}

