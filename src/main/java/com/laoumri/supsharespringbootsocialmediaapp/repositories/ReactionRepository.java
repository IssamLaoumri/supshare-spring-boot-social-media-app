package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.entities.Comment;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Profile;
import com.laoumri.supsharespringbootsocialmediaapp.entities.Reaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends Neo4jRepository<Reaction, UUID> {
    Optional<Reaction> findByProfileandComment(Profile profile, Comment comment);
    Optional<Reaction> findByProfileAndPost(Profile profile, Post post);
    List<Reaction> findByComment(Comment comment);
    List<Reaction> findByPost(Post post);
}
