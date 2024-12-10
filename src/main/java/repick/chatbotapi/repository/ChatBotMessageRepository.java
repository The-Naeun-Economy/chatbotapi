package repick.chatbotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;

@Repository
public interface ChatBotMessageRepository extends JpaRepository<ChatBotMessage, Long> {
}
