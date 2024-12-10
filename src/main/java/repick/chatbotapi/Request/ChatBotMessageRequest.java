package repick.chatbotapi.Request;

import lombok.Getter;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;

@Getter
public class ChatBotMessageRequest {
    private String message;
}
