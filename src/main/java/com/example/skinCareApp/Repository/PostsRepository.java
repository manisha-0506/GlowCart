package com.example.skinCareApp.Repository;

import com.example.skinCareApp.Entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByProductId(Long productId);
    // Add custom queries if needed
}

