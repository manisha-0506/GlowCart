package com.example.skinCareApp.Controller;
import com.example.skinCareApp.Entity.Posts;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Service.PostsService;
import com.example.skinCareApp.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Posts createPost(@RequestParam Long userId,@RequestParam Long productId, @RequestBody String content) {
        return postService.createPost(userId,productId, content);
    }

    @GetMapping("/getPost")
    public List<Posts> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{postId}/like")
    public Posts likePost(@PathVariable Long postId) {
        return postService.updateLikes(postId);
    }

    @GetMapping("/getPostsByCategory/{categoryId}")
    public List<Posts> getPostsByCategory(@PathVariable Long categoryId) {
        // Fetch all products in the category
        List<Product> productsInCategory = productService.getProductsByCategory(categoryId);

        // Fetch all posts and filter them based on product category
        List<Posts> posts = postService.getAllPosts();
        return posts.stream()
                .filter(post -> productsInCategory.contains(post.getProduct()))
                .collect(Collectors.toList());
    }
}
