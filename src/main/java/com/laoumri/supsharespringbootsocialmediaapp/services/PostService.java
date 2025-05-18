package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PostService {
    List<PostResponse> getPosts();
    PostResponse getPost(UUID id);
    PostResponse createPost(PostRequest postRequest, User user);
    PostResponse updatePost(UUID id, PostRequest postRequest);
    String deletePost(UUID id);
}
