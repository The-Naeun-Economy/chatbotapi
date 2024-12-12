package repick.chatbotapi.service;

import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotMessageResponse;

import java.util.List;

public interface ChatBotMessageService {
    ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request);
    List<ChatBotMessageResponse> getAllByChatRoomId(Long chatRoomId);
}
