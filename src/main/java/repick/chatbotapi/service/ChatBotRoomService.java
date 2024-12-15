package repick.chatbotapi.service;

import org.springframework.data.domain.Page;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotRoomResponse;

import java.util.List;
import java.util.UUID;

public interface ChatBotRoomService {
    Long userIdFromToken(String token);
    void createChatBotRoom(ChatBotRoom chatBotRoom);
    ChatBotRoom findUUIDChatBotRoom(UUID uuid);
    Long findIdUUID(UUID uuid);
    Page<ChatBotRoomResponse> findUserId(Long id, int page, int size);
    void deleteChatBotRoom(Long id);
}
