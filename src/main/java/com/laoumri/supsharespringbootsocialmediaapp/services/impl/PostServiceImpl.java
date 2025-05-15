package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
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
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;

    //Counters are not included on the NODE

    @Override
    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(post -> new PostResponse(
                        post.getPost_id(),
                        post.getContent(),
                        post.getMedia(),
                        post.getProfile().getFirstname()+" "+post.getProfile().getLastname(),
                        post.getCreated_at(),
                        commentRepository.findAllByPost(post).stream().count(),
                        reactionRepository.findByPost(post).stream().filter(r->r.getReactionType()== EReaction.LIKE).count(),
                        reactionRepository.findByPost(post).stream().filter(r->r.getReactionType()==EReaction.DISLIKE).count()
                ))
                .collect(Collectors.toList());
        return postResponses;
    }

    @Override
    public PostResponse getPost(UUID id) {
        var post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        PostResponse postR = postMapper.PostToPostResponse(post);
        postR.setComments_count(commentRepository.findAllByPost(post).stream().count());
        postR.setLikes_count(reactionRepository.findByPost(post)
                .stream()
                .filter(r->r.getReactionType()== EReaction.LIKE)
                .count());
        postR.setDislikes_count(reactionRepository.findByPost(post).stream()
                .filter(r->r.getReactionType()== EReaction.DISLIKE)
                .count());
        return postR;
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
        PostResponse response = postMapper.PostToPostResponse(postRepository.save(post));
        response.setComments_count(commentRepository.findAllByPost(post).stream().count());
        response.setDislikes_count(reactionRepository.findByPost(post)
                .stream()
                .filter(r->r.getReactionType()== EReaction.DISLIKE).count());
        response.setLikes_count(reactionRepository.findByPost(post)
                .stream()
                .filter(u->u.getReactionType()== EReaction.LIKE).count());
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
