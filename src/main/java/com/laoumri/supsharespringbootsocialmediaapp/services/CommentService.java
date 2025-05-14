package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.CommentRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {
    List<CommentResponse> getComments(UUID postId);
    CommentResponse getComment(UUID commentId);
    CommentResponse createComment(CommentRequest commentRequest, UUID user_id, UUID post_id);
    CommentResponse updateComment(UUID post_id, UUID comment_id,CommentRequest commentRequest);
    String deleteComment(UUID commentId, UUID post_id);
}
