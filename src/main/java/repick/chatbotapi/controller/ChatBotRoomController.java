package repick.chatbotapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repick.chatbotapi.Request.ChatBotMessageRequest;
import repick.chatbotapi.Request.ChatBotRoomRequest;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.response.ChatBotMessageResponse;
import repick.chatbotapi.response.ChatBotRoomResponse;
import repick.chatbotapi.service.ChatBotMessageService;
import repick.chatbotapi.service.ChatBotRoomService;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
public class ChatBotRoomController {

    private final ChatBotRoomService chatBotRoomService;
    private final ChatBotMessageService chatBotMessageService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<ChatBotRoomResponse> getChatBotRoomsByUserId(
            @RequestHeader String Authorization
    ) {
        Long userId = chatBotRoomService.userIdFromToken(Authorization);
        return chatBotRoomService.findUserId(userId);
    }

    @GetMapping("/{uuid}")
    public List<ChatBotMessageResponse> getUUIDChatBotRoom(@PathVariable String uuid) {
        Long chatRoomId = chatBotRoomService.findIdUUID(UUID.fromString(uuid));
        return chatBotMessageService.getAllByChatRoomId(chatRoomId);
    }

    @PostMapping
    public String createChatBotRoom(
            @RequestHeader String Authorization,
            @RequestBody ChatBotRoomRequest chatBotRoomRequest
            ){
        Long userId = chatBotRoomService.userIdFromToken(Authorization);
        ChatBotRoom chatBotRoom = chatBotRoomRequest.toEntity(userId);
        chatBotRoomService.createChatBotRoom(chatBotRoom);
        return "created";
    }

    @PostMapping("/message/{uuid}")
    public ChatBotMessageResponse llmMessage(@PathVariable String uuid, @RequestBody ChatBotMessageRequest chatBotMessageRequest) {
        ChatBotRoom chatBotRoom = chatBotRoomService.findUUIDChatBotRoom(UUID.fromString(uuid));
        String request = chatBotMessageRequest.getMessage();
        ChatBotMessage chatBotMessage = chatBotMessageService.sendChatBotMessageAndSave(chatBotRoom, request);
        return ChatBotMessageResponse.from(chatBotMessage);
    }
}
