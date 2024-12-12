package repick.chatbotapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.repository.ChatBotRoomRepository;
import repick.chatbotapi.response.ChatBotMessageResponse;
import repick.chatbotapi.response.ChatBotRoomResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatBotRoomServiceImpl implements ChatBotRoomService {

    private final ChatBotRoomRepository chatBotRoomRepository;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    public Long userIdFromToken(String bearerToken) {
        String token = bearerToken.substring(7);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    @Override
    public void createChatBotRoom(ChatBotRoom chatBotRoom) {
        chatBotRoomRepository.save(chatBotRoom);
    }

    @Override
    public ChatBotRoom findUUIDChatBotRoom(UUID uuid) {
        return chatBotRoomRepository.findByUuid(uuid);
    }

    @Override
    public Long findIdUUID(UUID uuid) {
        return chatBotRoomRepository.findIdByUuid(uuid);
    }

    @Override
    public List<ChatBotRoomResponse> findUserId(Long id) {
        List<ChatBotRoom> chatBotRooms = chatBotRoomRepository.findByOwnerId(id);
        return chatBotRooms.stream().map(ChatBotRoomResponse::from).collect(Collectors.toList());
    }
}
