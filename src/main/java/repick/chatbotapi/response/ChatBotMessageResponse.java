package repick.chatbotapi.response;

import lombok.Getter;
import repick.chatbotapi.domain.ChatBotMessage;

import java.util.List;

public record ChatBotMessageResponse (
        Long id,
        String request,
        String response
) {
    public static ChatBotMessageResponse from(ChatBotMessage message) {
        return new ChatBotMessageResponse(
                message.getId(),
                message.getRequest(),
                message.getResponse()
        );
    }
}
