package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Posts;
import com.example.skinCareApp.Entity.Users;
import com.example.skinCareApp.Service.PostsService;
import com.example.skinCareApp.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PostsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostsService postsService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private PostsController postsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postsController).build();
    }

    @Test
    void testCreatePost() throws Exception {
        Users user = new Users();
        user.setId(100L);
        Posts post = new Posts();
        post.setUser(user);
        post.setId(1L);
        post.setContent("Great Product");

        when(postsService.createPost(1L, 1L, "Great Product")).thenReturn(post);

        mockMvc.perform(post("/posts/create")
                        .param("userId", "1")
                        .param("productId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Great Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Great Product"));

        verify(postsService, times(1)).createPost(1L, 1L, "Great Product");
    }

    @Test
    void testGetAllPosts() throws Exception {
        Posts post1 = new Posts();
        post1.setId(1L);
        post1.setContent("Amazing product");

        Posts post2 = new Posts();
        post2.setId(2L);
        post2.setContent("Loved it!");

        List<Posts> postsList = Arrays.asList(post1, post2);

        when(postsService.getAllPosts()).thenReturn(postsList);

        mockMvc.perform(get("/posts/getPost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].content").value("Amazing product"))
                .andExpect(jsonPath("$[1].content").value("Loved it!"));

        verify(postsService, times(1)).getAllPosts();
    }

    @Test
    void testLikePost() throws Exception {
        Posts post = new Posts();
        post.setId(1L);
        post.setContent("Great Product");

        when(postsService.updateLikes(1L)).thenReturn(post);

        mockMvc.perform(put("/posts/1/like"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(postsService, times(1)).updateLikes(1L);
    }
}
