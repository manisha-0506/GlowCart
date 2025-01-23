package com.example.skinCareApp.Repository;

import com.example.skinCareApp.Entity.Cart;
import com.example.skinCareApp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long aLong);



}
