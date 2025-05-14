package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.mapper.CommentMapper;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.CommentRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.PostRepository;
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

    @Override
    public List<CommentResponse> getComments(UUID postId) {
        if(!postRepository.findById(postId).isPresent()){
            throw new RuntimeException("Post not found");
        }
        List<Comment> comments = postRepository.findById(postId).get().getComments();
        List<CommentResponse> commentResponses = comments.stream().map(
                com-> commentMapper.mapComment(com)).collect(Collectors.toList());
        return commentResponses;
    }

    @Override
    public CommentResponse getComment(UUID commentId) {
        return commentMapper.mapComment(commentRepository.findById(commentId).get());
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, UUID user_id, UUID post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(()->new RuntimeException("Post Not Found"));
        Comment comment = commentMapper.mapCommentRequest(commentRequest, user_id);
        post.getComments().add(comment);
        postRepository.save(post);
        return commentMapper.mapComment(comment);
    }

    @Override
    public CommentResponse updateComment(UUID post_id, UUID comment_id,CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("Comment Not Found"));
        comment.setUpdated(true);
        comment.setUpdated_at(Instant.now());
        comment.setMedia(commentRequest.getMedia());
        comment.setContent(commentRequest.getComment());
        Post post = postRepository.findById(post_id).get();
        post.getComments().remove(commentRepository.findById(comment_id).get());
        post.getComments().add(comment);
        postRepository.save(post);
        return commentMapper.mapComment(comment);
    }

    @Override
    public String deleteComment(UUID commentId, UUID post_id) {
        Post post = postRepository.findById(post_id).get();
        post.getComments().remove(commentRepository.findById(commentId).get());
        postRepository.save(post);
        return "Comment Deleted";
    }
}
