package backend_socket.backend_socket.messagecontent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageContentRepository extends JpaRepository<MessageContent, UUID> {
    Optional<MessageContent> findTopByMessageRoomIdOrderByDateSentDesc(final UUID messageRoomId);

    List<MessageContent> findByMessageRoomIdOrderByDateSent(final UUID messageRoomId);
}
