package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.ReactionRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ReactionResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Reaction;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ReactionService {
    public ReactionResponse ReactWithPost(ReactionRequest reactionRequest, UUID postid, UUID userid, EReaction reaction);
    public ReactionResponse ReactWithComment(ReactionRequest reactionRequest, UUID commentId, UUID userid, EReaction reaction);
}
