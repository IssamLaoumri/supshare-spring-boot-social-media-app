package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PostService {
    List<PostResponse> getPosts();
    PostResponse getPost(UUID id);
    PostResponse createPost(PostRequest postRequest, UUID userId);
    PostResponse updatePost(UUID id, PostRequest postRequest);

    String deletePost(UUID id);
}
