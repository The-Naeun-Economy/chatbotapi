package repick.chatbotapi.service;

import org.springframework.data.domain.Page;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotMessageResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChatBotMessageService {
    ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request);
    ChatBotMessage sendChatBotMessageAndSaveEc2(ChatBotRoom chatBotRoom, String request);
    CompletableFuture<ChatBotMessage> sendChatBotMessageAndSaveAsync(ChatBotRoom chatBotRoom, String request);
    CompletableFuture<ChatBotMessage> sendChatBotMessageAndSaveAsyncEc2(ChatBotRoom chatBotRoom, String request);
    Page<ChatBotMessageResponse> getAllByChatRoomId(Long chatRoomId, int page, int size);
    void deleteChatBotMessages(Long id);
}
