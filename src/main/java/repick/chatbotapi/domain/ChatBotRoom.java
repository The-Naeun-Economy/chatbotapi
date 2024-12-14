package repick.chatbotapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repick.chatbotapi.config.CreateTImeEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(indexes = {@Index(columnList = "uuid"), @Index(columnList = "lastModified")})
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private UUID uuid;
    private String title;
    private Long ownerId;
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "chatBotRoom")
    private Set<ChatBotMessage> messages;
}
