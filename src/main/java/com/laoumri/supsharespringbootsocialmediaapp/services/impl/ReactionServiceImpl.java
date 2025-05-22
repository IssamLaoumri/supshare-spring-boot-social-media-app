package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.dto.requests.ReactionRequest;
import com.laoumri.supsharespringbootsocialmediaapp.dto.responses.ReactionResponse;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Reaction;
import com.laoumri.supsharespringbootsocialmediaapp.entities.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.EReaction;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.*;
import com.laoumri.supsharespringbootsocialmediaapp.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public ReactionResponse ReactWithPost( UUID postid, User user, EReaction reaction) {
        Profile profile = profileRepository.findById(user.getProfile().getProfile()).get();
        Optional<Reaction> react = reactionRepository.findByProfileAndPost(profile, postRepository.findById(postid).get());
        if(react.isPresent()) {
            reactionRepository.deleteById(react.get().getReactionId());
            return new ReactionResponse("you unliked this post",profile.getFirstname()+" "+profile.getLastname());
        }
        Reaction reac = Reaction.builder()
                                .reactionType(reaction).
                                post(postRepository.findById(postid).get())
                                .profile(profile)
                                .comment(null)
                                .build();
        return new ReactionResponse("you "+reaction+" this post",profile.getFirstname()+" "+profile.getLastname());
    }

    @Override
    public ReactionResponse ReactWithComment(UUID commentId, User user, EReaction reaction) {
        Profile profile = profileRepository.findById(user.getProfile().getProfile()).get();
        Optional<Reaction> react = reactionRepository.findByProfileAndComment(profile, commentRepository.findById(commentId).get());
        if(react.isPresent()) {
            reactionRepository.deleteById(react.get().getReactionId());
            return new ReactionResponse("you unliked this post",profile.getFirstname()+" "+profile.getLastname());
        }
        Reaction reac = Reaction.builder()
                .reactionType(reaction).
                comment(commentRepository.findById(commentId).get())
                .profile(profile)
                .post(null)
                .build();
        return new ReactionResponse("you "+reaction+" this post",profile.getFirstname()+" "+profile.getLastname());
    }
}
