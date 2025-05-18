package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {
    List<CommentResponse> getComments(UUID postId);
    CommentResponse getComment(UUID commentId);
    CommentResponse createComment(CommentRequest commentRequest, User user, UUID post_id);
    CommentResponse updateComment(UUID post_id, UUID comment_id,CommentRequest commentRequest);
    String deleteComment(UUID commentId, UUID post_id);
}
