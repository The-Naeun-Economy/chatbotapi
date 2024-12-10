package repick.chatbotapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import repick.chatbotapi.domain.ChatBotRoom;
import repick.chatbotapi.repository.ChatBotRoomRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatBotRoomServiceImpl implements ChatBotRoomService {

    private final ChatBotRoomRepository chatBotRoomRepository;

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    public Long userIdFromToken(String token) {
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
}
