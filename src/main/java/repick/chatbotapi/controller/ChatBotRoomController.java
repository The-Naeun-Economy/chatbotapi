package repick.chatbotapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.concurrent.CompletableFuture;

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
    public Page<ChatBotRoomResponse> getChatBotRoomsByUserId(
            @RequestHeader String Authorization,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Long userId = chatBotRoomService.userIdFromToken(Authorization);
        return chatBotRoomService.findUserId(userId, page, size);
    }

    @DeleteMapping("/{uuid}")
    public String deleteChatBotRoom(@PathVariable String uuid) {
        Long chatRoomId = chatBotRoomService.findIdUUID(UUID.fromString(uuid));
        chatBotMessageService.deleteChatBotMessages(chatRoomId);
        chatBotRoomService.deleteChatBotRoom(chatRoomId);
        return "success";
    }

    @GetMapping("/{uuid}")
    public Page<ChatBotMessageResponse> getUUIDChatBotRoom(
            @PathVariable String uuid,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Long chatRoomId = chatBotRoomService.findIdUUID(UUID.fromString(uuid));
        return chatBotMessageService.getAllByChatRoomId(chatRoomId, page, size);
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

    @PostMapping("/message/{uuid}/async")
    public CompletableFuture<ChatBotMessageResponse> llmMessageAsync(@PathVariable String uuid, @RequestBody ChatBotMessageRequest chatBotMessageRequest) {
        ChatBotRoom chatBotRoom = chatBotRoomService.findUUIDChatBotRoom(UUID.fromString(uuid));
        String request = chatBotMessageRequest.getMessage();
        return chatBotMessageService.sendChatBotMessageAndSaveAsync(chatBotRoom, request).thenApply(ChatBotMessageResponse::from);
    }
}
