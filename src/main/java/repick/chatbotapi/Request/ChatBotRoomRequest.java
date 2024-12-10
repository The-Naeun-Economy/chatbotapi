package repick.chatbotapi.Request;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repick.chatbotapi.domain.ChatBotRoom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
public class ChatBotRoomRequest {
    private UUID uuid;
    private String title;

    public ChatBotRoom toEntity(Long ownerId) {
        return ChatBotRoom.builder()
                .uuid(uuid)
                .title(title)
                .ownerId(ownerId)
                .build();
    }
}