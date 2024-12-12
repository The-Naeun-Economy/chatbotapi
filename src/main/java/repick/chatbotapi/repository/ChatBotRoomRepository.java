package repick.chatbotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotRoom;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatBotRoomRepository extends JpaRepository<ChatBotRoom, Long> {
    ChatBotRoom findByUuid(UUID uuid);

    @Query("select r.id from ChatBotRoom r where r.uuid = :uuid")
    Long findIdByUuid(@Param("uuid") UUID uuid);


    List<ChatBotRoom> findByOwnerId(Long ownerId);
}
