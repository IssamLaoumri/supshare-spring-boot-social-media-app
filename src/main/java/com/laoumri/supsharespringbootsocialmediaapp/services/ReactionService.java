package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.ReactionRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ReactionResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Reaction;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ReactionService {
    public ReactionResponse ReactWithPost( UUID postid, User user, EReaction reaction);
    public ReactionResponse ReactWithComment(UUID commentId, User user, EReaction reaction);
}
