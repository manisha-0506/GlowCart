package com.example.skinCareApp.Controller;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class usersController {

    private final UsersService usersService;

    public usersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@Valid @RequestBody Users user) {
        System.out.println("hello"+user.getPassword());
        Users registeredUser = usersService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }


    @GetMapping("/{username}")
    public Users getUser(@Valid @PathVariable String username) {

        return usersService.findUserByUsername(username);
    }
    @GetMapping("/{id}")
    public Optional<Users> getById(@PathVariable Long id) {

        return usersService.findById(id);
    }
}

