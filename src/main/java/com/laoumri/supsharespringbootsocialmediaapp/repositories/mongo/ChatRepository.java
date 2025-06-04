package com.laoumri.supsharespringbootsocialmediaapp.repositories.mongo;

import com.laoumri.supsharespringbootsocialmediaapp.documents.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<ChatMessage, String> {
}
