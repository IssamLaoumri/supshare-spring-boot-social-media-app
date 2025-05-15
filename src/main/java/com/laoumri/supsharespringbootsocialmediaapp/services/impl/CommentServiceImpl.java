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
        List<Comment> comments = commentRepository.findAllByPost(postRepository.findById(postId).get());
        List<CommentResponse> responses = comments.stream().map(com->CommentResponse.builder()
                .comment(com.getContent())
                .comment_id(com.getComment_id()).media(com.getMedia()).created_at(com.getCreated_at()).updated_at(com.getCreated_at())
                .updated(false).dislikes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.DISLIKE).count())
                .likes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.LIKE).count())
                        .author(com.getAuthor().getFirstname()+" "+com.getAuthor().getLastname())
                .build())
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public CommentResponse getComment(UUID commentId) {
        Comment com = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment does not exist"));
        return CommentResponse.builder().comment_id(commentId)
                .author(com.getAuthor().getFirstname()+" "+com.getAuthor().getLastname())
                .created_at(com.getCreated_at())
                .updated_at(com.getUpdated_at())
                .comment(com.getContent())
                .media(com.getMedia())
                .updated(com.isUpdated())
                .likes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.LIKE).count())
                .dislikes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.DISLIKE).count())
                .build();
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
        Comment com = commentRepository.findById(comment_id).get();
        com.setUpdated(true);
        com.setMedia(commentRequest.getMedia());
        com.setContent(commentRequest.getComment());
        com.setUpdated_at(Instant.now());
        commentRepository.save(com);
        return CommentResponse.builder().comment_id(com.getComment_id())
                .author(com.getAuthor().getFirstname()+" "+com.getAuthor().getLastname())
                .created_at(com.getCreated_at())
                .updated_at(com.getUpdated_at())
                .comment(com.getContent())
                .media(com.getMedia())
                .updated(com.isUpdated())
                .likes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.LIKE).count())
                .dislikes_count(reactionRepository.findByComment(com).stream().filter(c->c.getReactionType()==EReaction.DISLIKE).count())
                .build();
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
