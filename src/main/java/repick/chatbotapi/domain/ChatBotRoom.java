package repick.chatbotapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repick.chatbotapi.config.CreateTImeEntity;

import java.util.Set;
import java.util.UUID;

@Table(indexes = {@Index(columnList = "uuid")})
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

    @OneToMany(mappedBy = "chatBotRoom")
    private Set<ChatBotMessage> messages;
}
