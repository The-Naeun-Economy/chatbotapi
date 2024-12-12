package repick.chatbotapi.response;

import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;

import java.util.UUID;

public record ChatBotRoomResponse(
        Long id,
        UUID uuid,
        String title
) {
    public static ChatBotRoomResponse from(ChatBotRoom chatBotRoom) {
        return new ChatBotRoomResponse(
                chatBotRoom.getId(),
                chatBotRoom.getUuid(),
                chatBotRoom.getTitle()
        );
    }
}
