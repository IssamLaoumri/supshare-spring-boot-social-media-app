package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import com.laoumri.supsharespringbootsocialmediaapp.mapper.PostMapper;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.*;
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
    private final PostMapper postMapper;
    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;

    //Counters are not included on the NODE

    @Override
    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(p->postMapper.PostToPostResponse(p))
                .collect(Collectors.toList());
        return postResponses;
    }

    @Override
    public PostResponse getPost(UUID id) {
        var post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        return postMapper.PostToPostResponse(post);
    }

    @Override
    public PostResponse createPost(PostRequest postRequest, User user) {
        var prf = profileRepository.findById(user.getProfile().getProfile())
                .orElseThrow(()->new RuntimeException("Profile not found"));
        Post post = postMapper.PostRequestToPost(postRequest, prf);
        return postMapper.PostToPostResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(UUID id, PostRequest postRequest) {
        var post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        post.setContent(postRequest.getContent());
        post.setMedia(postRequest.getMedia());
        PostResponse response = postMapper.PostToPostResponse(postRepository.save(post));
        return response;
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
