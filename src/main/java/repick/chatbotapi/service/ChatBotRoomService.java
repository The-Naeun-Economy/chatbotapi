package repick.chatbotapi.service;

import repick.chatbotapi.domain.ChatBotRoom;

import java.util.UUID;

public interface ChatBotRoomService {
    Long userIdFromToken(String token);
    void createChatBotRoom(ChatBotRoom chatBotRoom);
    ChatBotRoom findUUIDChatBotRoom(UUID uuid);
}
