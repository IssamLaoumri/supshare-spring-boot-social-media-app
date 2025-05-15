package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.mapper.CommentMapper;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.CommentRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.PostRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.ProfileRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
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

    @Override
    public List<CommentResponse> getComments(UUID postId) {
        if(!postRepository.existsById(postId)){
            throw new RuntimeException("Post does not exist");
        }
        List<Comment> comments = commentRepository.findAllByPost(postRepository.findById(postId).get());
        List<CommentResponse> responses = comments.stream().map(com->commentMapper.mapComment(com)).collect(Collectors.toList());
        return responses;
    }

    @Override
    public CommentResponse getComment(UUID commentId) {
        return commentMapper.mapComment(commentRepository.findById(commentId).get());
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, UUID user_id, UUID post_id) {
        if(!postRepository.existsById(post_id)){
            throw new RuntimeException("Post does not exist");
        }
        Profile profile = profileRepository.findByUsername(userRepository.findById(user_id).get().getUsername()).orElseThrow(()->new RuntimeException("User does not exist"));
        Comment comment = Comment.builder()
                .created_at(Instant.now())
                .updated_at(Instant.now())
                .post(postRepository.findById(post_id).get())
                .media(commentRequest.getMedia())
                .content(commentRequest.getComment())
                .author(profile)
                .updated(false)
                .build();
        commentRepository.save(comment);
        return commentMapper.mapComment(comment);
    }

    @Override
    public CommentResponse updateComment(UUID post_id, UUID comment_id,CommentRequest commentRequest) {
        if(!postRepository.existsById(post_id)){
            throw new RuntimeException("Post does not exist");
        }
        Comment comment = commentRepository.findById(comment_id).get();
        comment.setUpdated(true);
        comment.setMedia(commentRequest.getMedia());
        comment.setContent(commentRequest.getComment());
        comment.setUpdated_at(Instant.now());
        commentRepository.save(comment);
        return commentMapper.mapComment(comment);
    }

    @Override
    public String deleteComment(UUID commentId, UUID post_id) {
        if(!postRepository.existsById(post_id)){
            throw new RuntimeException("Post does not exist");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Comment does not exist"));
        commentRepository.delete(comment);
        return "Comment deleted";
    }
}
