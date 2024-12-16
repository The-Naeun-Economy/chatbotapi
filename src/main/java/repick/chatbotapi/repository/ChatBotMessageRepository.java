package repick.chatbotapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotMessage;

@Repository
public interface ChatBotMessageRepository extends JpaRepository<ChatBotMessage, Long> {
    @Query("select m from ChatBotMessage m where m.chatBotRoom.id = :id")
    Page<ChatBotMessage> findByChatBotRoomId(@Param("id") Long id, Pageable pageable);

    @Modifying
    @Query("DELETE FROM ChatBotMessage m WHERE m.chatBotRoom.id = :id")
    void deleteByChatBotRoomId(@Param("id") Long id);
}
