package backend_socket.backend_socket.messageroommember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageRoomMemberService {
    private final MessageRoomMemberRepository messageRoomMemberRepository;
    private final MessageRoomMemberMapper messageRoomMemberMapper;

    public List<MessageRoomMemberDto> findMessageRoomId(final UUID messageRoomId) {
        return messageRoomMemberRepository.findByMessageRoomId(messageRoomId)
                .stream()
                .map(m -> messageRoomMemberMapper.toDTO(m, new MessageRoomMemberDto()))
                .toList();
    }
}
