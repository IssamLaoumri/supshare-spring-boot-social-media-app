package com.laoumri.supsharespringbootsocialmediaapp.controllers;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.ReactionRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ReactionResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import com.laoumri.supsharespringbootsocialmediaapp.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping("/likePost/{post_id}")
    public ReactionResponse LikePost(@PathVariable UUID post_id,@AuthenticationPrincipal User user) {
        return reactionService.ReactWithPost(post_id,user, EReaction.LIKE);
    }

    @PostMapping("/dislikePost/{post_id}")
    public ReactionResponse DislikePost(@PathVariable UUID post_id, ReactionRequest reactionRequest, @AuthenticationPrincipal User user) {
        return reactionService.ReactWithPost(post_id, user, EReaction.DISLIKE);
    }

    @PostMapping("/likeComment/{comment_id}")
    public ReactionResponse LikeComment(@PathVariable UUID comment_id, ReactionRequest reactionRequest, @AuthenticationPrincipal User user) {
        return reactionService.ReactWithComment(comment_id, user, EReaction.LIKE);
    }

    @PostMapping("/dislikeComment/{comment_id}")
    public ReactionResponse DislikeComment(@PathVariable UUID comment_id, ReactionRequest reactionRequest, @AuthenticationPrincipal User user){
        return reactionService.ReactWithComment(comment_id, user, EReaction.DISLIKE);
    }
}