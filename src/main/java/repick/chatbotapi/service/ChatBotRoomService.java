package repick.chatbotapi.service;

import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotRoomResponse;

import java.util.List;
import java.util.UUID;

public interface ChatBotRoomService {
    Long userIdFromToken(String token);
    void createChatBotRoom(ChatBotRoom chatBotRoom);
    ChatBotRoom findUUIDChatBotRoom(UUID uuid);
    Long findIdUUID(UUID uuid);
    List<ChatBotRoomResponse> findUserId(Long id);
}
