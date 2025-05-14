package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.mapper.PostMapper;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.PostRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.ProfileRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(post -> new PostResponse(
                        post.getPost_id(),
                        post.getContent(),
                        post.getMedia(),
                        post.getProfile().getFirstname()+" "+post.getProfile().getLastname(),
                        post.getCreated_at()
                ))
                .collect(Collectors.toList());
        return postResponses;
    }

    @Override
    public PostResponse getPost(UUID id) {
        var post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        return postMapper.PostToPostResponse(post);
    }

    @Override
    public PostResponse createPost(PostRequest postRequest, UUID userId) {
        var user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        var prf = profileRepository.findByUsername(user.getUsername()).orElseThrow(()->new RuntimeException("Profile not found"));
        Post post = Post.builder().content(postRequest.getContent()).profile(prf).created_at(Instant.now()).media(postRequest.getMedia()).build();
        return postMapper.PostToPostResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(UUID id, PostRequest postRequest) {
        var post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        post.setContent(postRequest.getContent());
        post.setMedia(postRequest.getMedia());
        return postMapper.PostToPostResponse(postRepository.save(post));
    }

    @Override
    public String deletePost(UUID id) {
        if(!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
        return "Post deleted";
    }
}
