package com.laoumri.supsharespringbootsocialmediaapp.documents;

import com.laoumri.supsharespringbootsocialmediaapp.enums.EMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.Id;

import java.time.Instant;

@Document(collection = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String message;
    private EMessageStatus status;
    private Instant timestamp;
}
