package backend_socket.backend_socket.messagecontent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public MessageContentDto save(final MessageContentDto messageContentDto) {
        final MessageContent messageContent = messageContentRepository.save(messageContentMapper.toEntity(messageContentDto, new MessageContent()));
        return messageContentMapper.toDto(messageContent, new MessageContentDto());
    }

    public List<MessageContentDto> getMessagesByRoomId(final UUID roomId) {
        return messageContentRepository.findByMessageRoomIdOrderByDateSent(roomId)
                .stream()
                .map(m -> messageContentMapper.toDto(m, new MessageContentDto()))
                .toList();
    }
}
