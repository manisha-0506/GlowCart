package com.example.skinCareApp.ServiceTest;

import com.example.skinCareApp.Entity.Posts;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Repository.PostsRepository;
import com.example.skinCareApp.Repository.ProductRepository;
import com.example.skinCareApp.Repository.UsersRepository;
import com.example.skinCareApp.ServiceImpl.PostsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsServiceImplTest {

    @Mock
    private PostsRepository postsRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PostsServiceImpl postsService;

    private Users user;
    private Product product;
    private Posts post;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        post = new Posts();
        post.setId(1L);
        post.setUser(user);
        post.setProduct(product);
    }

    @Test
    void testCreatePost() {
        String expectedContent = "Great Product";

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Capture the post object passed to save()
        ArgumentCaptor<Posts> postCaptor = ArgumentCaptor.forClass(Posts.class);
        when(postsRepository.save(any(Posts.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Posts createdPost = postsService.createPost(1L, 1L, expectedContent);

        // Verify that the postRepository.save() method was called
        verify(postsRepository).save(postCaptor.capture());

        // Retrieve the captured post and verify content
        Posts capturedPost = postCaptor.getValue();

        assertNotNull(createdPost);
        assertEquals(expectedContent, capturedPost.getContent());
        assertEquals(user, capturedPost.getUser());
        assertEquals(product, capturedPost.getProduct());
    }
}
