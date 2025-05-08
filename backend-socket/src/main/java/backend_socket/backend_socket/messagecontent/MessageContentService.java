package backend_socket.backend_socket.messagecontent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageContentService {
    private final MessageContentRepository messageContentRepository;
    private final MessageContentMapper messageContentMapper;


    public MessageContentDto getLastMessage(final UUID messageRoomId) {
        return messageContentRepository.findTopByMessageRoomIdOrderByDateSentDesc(messageRoomId)
                .map(m -> messageContentMapper.toDto(m, new MessageContentDto()))
                .orElse(null);
    }
}
