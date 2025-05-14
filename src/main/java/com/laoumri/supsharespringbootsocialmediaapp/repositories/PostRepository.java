package com.laoumri.supsharespringbootsocialmediaapp.repositories;

import com.laoumri.supsharespringbootsocialmediaapp.entities.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends Neo4jRepository<Post, UUID> {
}
