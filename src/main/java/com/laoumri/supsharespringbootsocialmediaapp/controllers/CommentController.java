package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable UUID commentId) {
        return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
    }

    @GetMapping("/all/{post_id}")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable UUID post_id) {
        return new ResponseEntity<>(commentService.getComments(post_id), HttpStatus.OK);
    }

    @PostMapping("/add/{post_id}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable UUID post_id, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(commentService.createComment(commentRequest,user.getId(),post_id), HttpStatus.CREATED);
    }

    @PutMapping("/update/{post_id}/{comment_id}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest commentRequest,@PathVariable UUID post_id,@PathVariable UUID comment_id) {
        return new ResponseEntity<>(commentService.updateComment(post_id,comment_id,commentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{post_id}/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable UUID post_id,@PathVariable UUID comment_id) {
        return new ResponseEntity<>(commentService.deleteComment(post_id,comment_id), HttpStatus.OK);
    }
}
