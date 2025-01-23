package com.example.skinCareApp.ServiceImpl;


import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Exception.UserNotFoundException;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private final UsersRepository userRepository;

    public UsersServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users findUserByUsername(String username) {
        return userRepository.findByUsername(username);//orElseThrow(() -> new UserNotFoundException("User with NAME " + username + " not found"));;
    }
    @Override
    public Optional<Users> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found")));
    }
}

