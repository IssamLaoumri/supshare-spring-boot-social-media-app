package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import com.laoumri.supsharespringbootsocialmediaapp.mapper.CommentMapper;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.*;
import com.laoumri.supsharespringbootsocialmediaapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    // Likes Counter is not Included on the NODE

    @Override
    public List<CommentResponse> getComments(UUID postId) {
        if(!postRepository.existsById(postId)){
            throw new RuntimeException("Post does not exist");
        }
        List<Comment> comments = postRepository.findById(postId).get().getComments();
        List<CommentResponse> commentR = comments.stream()
                .map(com -> commentMapper.toPostResponse(com))
                .collect(Collectors.toList());
        return commentR;
    }

    @Override
    public CommentResponse getComment(UUID commentId) {
        return commentMapper.toPostResponse(commentRepository.findById(commentId)
                .orElseThrow(()->new RuntimeException("Comment not found")));
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, User user, UUID post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("post not found"));
        Comment comment = commentMapper.toComment(commentRequest, user, post);
        post.getComments().add(comment);
        postRepository.save(post);
        return commentMapper.toPostResponse(comment);
    }
    // stackoverflow error
    @Override
    public CommentResponse updateComment(UUID post_id, UUID comment_id,CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("Comment not found"));
        Post post = postRepository.findById(post_id).orElseThrow(()->new RuntimeException("Post not found"));
        post.getComments().remove(comment);
        comment.setUpdated_at(Instant.now());
        comment.setUpdated(true);
        comment.setMedia(commentRequest.getMedia());
        comment.setContent(commentRequest.getComment());
        post.getComments().add(comment);
        return commentMapper.toPostResponse(comment);
    }

    @Override
    public String deleteComment(UUID commentId, UUID post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(()->new RuntimeException("post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Comment not found"));
        post.getComments().remove(comment);
        commentRepository.delete(comment);
        return "Comment deleted";
    }
}
