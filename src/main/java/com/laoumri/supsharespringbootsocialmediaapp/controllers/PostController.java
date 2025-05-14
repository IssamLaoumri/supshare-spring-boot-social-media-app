package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.PostRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.PostResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        if(postService.getPosts().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID post_id) {
        return new ResponseEntity<>(postService.getPost(post_id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal User user) {
        if(user.getId() == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        var post = postService.createPost(postRequest,user.getId());
        if(post == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/update/{post_id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID post_id, @RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postService.updatePost(post_id,postRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable UUID post_id) {
        return new ResponseEntity<>(postService.deletePost(post_id), HttpStatus.OK);
    }
}
