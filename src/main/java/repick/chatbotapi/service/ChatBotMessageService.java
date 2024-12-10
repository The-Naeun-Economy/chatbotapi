package repick.chatbotapi.service;

import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;

public interface ChatBotMessageService {
    ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request);
}
