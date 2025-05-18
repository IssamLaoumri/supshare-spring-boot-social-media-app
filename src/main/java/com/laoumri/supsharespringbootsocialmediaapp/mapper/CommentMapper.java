package com.laoumri.supsharespringbootsocialmediaapp.mapper;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.ProfileRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ProfileRepository profileRepository;

    public CommentResponse toPostResponse(Comment comment) {
        return CommentResponse.builder()
                .comment_id(comment.getComment_id())
                .media(comment.getMedia())
                .comment(comment.getContent())
                .updated(comment.isUpdated())
                .author(comment.getAuthor().getFirstname()+" "+comment.getAuthor().getLastname())
                .likes_count(comment.getReaction().stream().filter(r->r.getReactionType()== EReaction.LIKE).count())
                .dislikes_count(comment.getReaction().stream().filter(r->r.getReactionType()==EReaction.DISLIKE).count())
                .updated_at(comment.getUpdated_at())
                .created_at(comment.getCreated_at())
                .build();
    }

    public Comment toComment(CommentRequest commentRequest, User user, Post post) {
        Profile profile = profileRepository.findByUsername(user.getUsername()).get();
        return Comment.builder()
                .content(commentRequest.getComment())
                .media(commentRequest.getMedia())
                .updated_at(Instant.now())
                .updated(false)
                .created_at(Instant.now())
                .post(post)
                .author(profile)
                .reaction(List.of())
                .build();
    }
}
