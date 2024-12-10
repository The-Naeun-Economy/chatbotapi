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
import repick.chatbotapi.service.ChatBotMessageService;
import repick.chatbotapi.service.ChatBotRoomService;

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

    @GetMapping("/{uuid}")
    public ChatBotRoom getUUIDChatBotRoom(
            @PathVariable String uuid
    ) {
        return chatBotRoomService.findUUIDChatBotRoom(UUID.fromString(uuid));
    }

    @PostMapping
    public void createChatBotRoom(
            @RequestHeader String Authorization,
            @RequestBody ChatBotRoomRequest chatBotRoomRequest
            ){
        Long userId = chatBotRoomService.userIdFromToken(Authorization);
        ChatBotRoom chatBotRoom = chatBotRoomRequest.toEntity(userId);
        chatBotRoomService.createChatBotRoom(chatBotRoom);
    }

    @PostMapping("/message/{uuid}")
    public ChatBotMessageResponse llmMessage(@PathVariable String uuid, @RequestBody ChatBotMessageRequest chatBotMessageRequest) {
        ChatBotRoom chatBotRoom = getUUIDChatBotRoom(uuid);
        String request = chatBotMessageRequest.getMessage();
        ChatBotMessage chatBotMessage = chatBotMessageService.sendChatBotMessageAndSave(chatBotRoom, request);
        return ChatBotMessageResponse.from(chatBotMessage);
    }
}
