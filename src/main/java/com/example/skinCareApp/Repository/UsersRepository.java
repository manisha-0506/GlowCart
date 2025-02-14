package com.example.skinCareApp.Repository;

import com.example.skinCareApp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
   Users findByUsername(String username);

   Optional<Users> findById(Long id);

}

