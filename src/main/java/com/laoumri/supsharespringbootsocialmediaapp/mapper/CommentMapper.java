package com.laoumri.supsharespringbootsocialmediaapp.mapper;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.ProfileRepository;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public CommentResponse mapComment(Comment comment) {
        return CommentResponse.builder()
                .comment_id(comment.getComment_id())
                .media(comment.getMedia())
                .comment(comment.getContent())
                .author(comment.getAuthor().getFirstname()+" "+comment.getAuthor().getLastname())
                .created_at(comment.getCreated_at())
                .updated_at(comment.getUpdated_at())
                .updated(comment.isUpdated())
                .dislikes_count(0)
                .likes_count(0)
                .build();
    }

    public Comment mapCommentRequest(CommentRequest request, UUID user_id) {
        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("User Not Found"));
        Profile profile = profileRepository.findByUsername(user.getUsername()).orElseThrow(()->new RuntimeException("Profile Not Found"));
        return Comment.builder()
                .content(request.getComment())
                .media(request.getMedia())
                .author(profile)
                .created_at(Instant.now())
                .updated_at(Instant.now())
                .updated(false)
                .build();
    }
}
