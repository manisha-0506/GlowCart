package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Posts;

import java.util.List;

public interface PostsService {
    Posts createPost(Long userId,Long PID, String content);

    List<Posts> getAllPosts();

    Posts updateLikes(Long postId);
}

