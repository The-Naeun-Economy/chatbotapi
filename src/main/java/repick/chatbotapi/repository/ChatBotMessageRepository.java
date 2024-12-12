package repick.chatbotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotMessage;
import repick.chatbotapi.domain.ChatBotRoom;

import java.util.List;

@Repository
public interface ChatBotMessageRepository extends JpaRepository<ChatBotMessage, Long> {
    @Query("select m from ChatBotMessage m where m.chatBotRoom.id = :id")
    List<ChatBotMessage> findByChatBotRoomId(@Param("id") Long id);
}
