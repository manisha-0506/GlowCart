package com.example.skinCareApp.ServiceImpl;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Entity.Posts;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.PostsRepository;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.Repository.ProductRepository;
import com.example.skinCareApp.Repository.CategoryRepository;
import com.example.skinCareApp.Service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired// Assuming you have a UsersRepository
    private ProductRepository productRepository;

    @Override
    public Posts createPost(Long userId,Long productId, String content) {
            // Fetch the user and product based on the IDs
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

            // Create and save the post
            Posts post = new Posts();
            post.setContent(content);
            post.setUser(user);
            post.setProduct(product);

            return postsRepository.save(post);
        }

    @Override
    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Posts updateLikes(Long postId) {
        Optional<Posts> optionalPost = postsRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new RuntimeException("Post not found with ID: " + postId);
        }

        Posts post = optionalPost.get();
        post.setLikes(post.getLikes() + 1); // Increment likes by 1
        return postsRepository.save(post);
    }
}

