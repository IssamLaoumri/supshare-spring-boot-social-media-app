package com.laoumri.supsharespringbootsocialmediaapp.mapper;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class PostMapper {
    public PostResponse PostToPostResponse(Post post) {
        return PostResponse.builder()
                .post_id(post.getPost_id())
                .name(post.getProfile().getFirstname()+" "+post.getProfile().getLastname())
                .content(post.getContent())
                .media(post.getMedia())
                .created_at(post.getCreated_at())
                .likes_count(post.getReactions().stream().filter(l->l.getReactionType()== EReaction.LIKE).count())
                .dislikes_count(post.getReactions().stream().filter(l->l.getReactionType()== EReaction.LIKE).count())
                .comments_count(post.getComments().stream().count())
                .build();
    }

    public Post PostRequestToPost(PostRequest postRequest, Profile profile) {
        return Post.builder()
                .media(postRequest.getMedia())
                .content(postRequest.getContent())
                .created_at(Instant.now())
                .comments(List.of())
                .profile(profile)
                .reactions(List.of())
                .build();
    }
}
