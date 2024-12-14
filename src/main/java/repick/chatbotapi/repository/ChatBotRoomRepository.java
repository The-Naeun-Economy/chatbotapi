package repick.chatbotapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import repick.chatbotapi.domain.ChatBotRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChatBotRoomRepository extends JpaRepository<ChatBotRoom, Long> {
    ChatBotRoom findByUuid(UUID uuid);

    @Query("select r.id from ChatBotRoom r where r.uuid = :uuid")
    Long findIdByUuid(@Param("uuid") UUID uuid);

    @Query("select r from ChatBotRoom r where r.ownerId = : ownerId order by r.lastModified DESC")
    Page<ChatBotRoom> findByOwnerId(@Param("ownerId") Long ownerId, Pageable pageable);

    @Modifying
    @Query("UPDATE ChatBotRoom r SET r.lastModified = :lastModified WHERE r.uuid = :uuid")
    void updateLastModified(@Param("uuid") UUID uuid, @Param("lastModified") LocalDateTime lastModified);

}
