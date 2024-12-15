package repick.chatbotapi.service;

import org.springframework.data.domain.Page;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotMessageResponse;

import java.util.List;

public interface ChatBotMessageService {
    ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request);
    Page<ChatBotMessageResponse> getAllByChatRoomId(Long chatRoomId, int page, int size);
    void deleteChatBotMessages(Long id);
}
