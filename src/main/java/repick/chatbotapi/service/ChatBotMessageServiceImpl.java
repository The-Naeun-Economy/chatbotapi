package repick.chatbotapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import repick.chatbotapi.Dto.ChatBotMessageDto;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.repository.ChatBotMessageRepository;
import repick.chatbotapi.response.ChatBotMessageResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatBotMessageServiceImpl implements ChatBotMessageService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final ChatBotMessageRepository chatBotMessageRepository;

    @Override
    public List<ChatBotMessageResponse> getAllByChatRoomId(Long chatRoomId) {
        List<ChatBotMessage> messages = chatBotMessageRepository.findByChatBotRoomId(chatRoomId);
        return messages.stream().map(ChatBotMessageResponse::from).collect(Collectors.toList());
    }

    @Override
    public ChatBotMessage sendChatBotMessageAndSave(ChatBotRoom chatBotRoom, String request) {
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
        return chatBotMessageRepository.save(chatBotMessage);
    }
}
