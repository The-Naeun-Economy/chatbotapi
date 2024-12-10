package repick.chatbotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotRoom;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatBotRoomRepository extends JpaRepository<ChatBotRoom, Long> {
    ChatBotRoom findByUuid(UUID uuid);
}
