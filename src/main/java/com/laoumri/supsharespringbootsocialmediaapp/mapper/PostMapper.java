package com.laoumri.supsharespringbootsocialmediaapp.mapper;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import org.springframework.stereotype.Component;

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
                .build();
    }

    public Post PostRequestToPost(PostRequest postRequest) {
        return Post.builder()
                .media(List.of(postRequest.getMedia()))
                .content(postRequest.getContent())
                .build();
    }
}
