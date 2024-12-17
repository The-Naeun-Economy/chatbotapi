package repick.chatbotapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import repick.chatbotapi.Dto.ChatBotMessageDto;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.repository.ChatBotMessageRepository;
import repick.chatbotapi.repository.ChatBotRoomRepository;
import repick.chatbotapi.response.ChatBotMessageResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotMessageServiceImpl implements ChatBotMessageService {

    @Value("${llm.url}")
    private String llmUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final ChatBotRoomRepository chatBotRoomRepository;
    private final ChatBotMessageRepository chatBotMessageRepository;

    @Override
    public Page<ChatBotMessageResponse> getAllByChatRoomId(Long chatRoomId, int page, int size) {
        Page<ChatBotMessage> messages = chatBotMessageRepository.findByChatBotRoomId(chatRoomId, PageRequest.of(page, size));
        return messages.map(ChatBotMessageResponse::from);
    }

    @Override
    public ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request) {
        String url = llmUrl+"/api/v1/chat/sendMessage";
        String response = webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(Map.of("request",request))
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println(response);
        ChatBotMessage chatBotMessage = ChatBotMessage.builder()
                .chatBotRoom(chatBotRoom)
                .request(request)
                .response(response)
                .build();
        chatBotRoomRepository.updateLastModified(chatBotRoom.getUuid(),LocalDateTime.now());
        return chatBotMessageRepository.save(chatBotMessage);
    }

    @Async
    @Override
    public CompletableFuture<ChatBotMessage> sendChatBotMessageAndSaveAsync(ChatBotRoom chatBotRoom, String request) {
        ChatBotMessage chatBotMessage = sendChatBotMessageAndSave(chatBotRoom, request);
        return CompletableFuture.completedFuture(chatBotMessage);
    }

    @Async
    @Override
    public CompletableFuture<ChatBotMessage> sendChatBotMessageAndSaveAsyncEc2(ChatBotRoom chatBotRoom, String request) {
        ChatBotMessage chatBotMessage = sendChatBotMessageAndSaveEc2(chatBotRoom, request);
        return CompletableFuture.completedFuture(chatBotMessage);
    }

    @Override
    public ChatBotMessage sendChatBotMessageAndSaveEc2(ChatBotRoom chatBotRoom, String request) {
        String url = "http://ec2-13-208-253-99.ap-northeast-3.compute.amazonaws.com:8000/api/v1/chat/sendMessage";
        String response = webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(Map.of("request",request))
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println(response);
        ChatBotMessage chatBotMessage = ChatBotMessage.builder()
                .chatBotRoom(chatBotRoom)
                .request(request)
                .response(response)
                .build();
        chatBotRoomRepository.updateLastModified(chatBotRoom.getUuid(),LocalDateTime.now());
        return chatBotMessageRepository.save(chatBotMessage);
    }

    @Override
    public void deleteChatBotMessages(Long id) {
        chatBotMessageRepository.deleteByChatBotRoomId(id);
    }
}
