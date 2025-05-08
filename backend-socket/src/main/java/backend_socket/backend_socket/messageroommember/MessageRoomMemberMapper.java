package backend_socket.backend_socket.messageroommember;

import backend_socket.backend_socket.messageroom.MessageRoom;
import backend_socket.backend_socket.messageroom.MessageRoomRepository;
import backend_socket.backend_socket.user.User;
import backend_socket.backend_socket.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageRoomMemberMapper {
    private final UserRepository userRepository;
    private final MessageRoomRepository messageRoomRepository;


    public MessageRoomMemberDto toDTO(final MessageRoomMember messageRoomMember, final MessageRoomMemberDto messageRoomMemberDTO) {
        messageRoomMemberDTO.setMessageRoomId(messageRoomMember.getMessageRoom().getId());
        messageRoomMemberDTO.setUsername(messageRoomMember.getUser().getUsername());
        messageRoomMemberDTO.setIsAdmin(messageRoomMember.getIsAdmin());
        messageRoomMemberDTO.setLastSeen(messageRoomMember.getLastSeen());
        messageRoomMemberDTO.setLastLogin(messageRoomMember.getUser().getLastLogin());
        return messageRoomMemberDTO;
    }


    public MessageRoomMember toEntity(final MessageRoomMemberDto messageRoomMemberDTO, final MessageRoomMember messageRoomMember) {
        final MessageRoom messageRoom = messageRoomMemberDTO.getMessageRoomId() == null ? null : messageRoomRepository.findById(messageRoomMemberDTO.getMessageRoomId())
                .orElseThrow(() -> new EntityNotFoundException("MessageRoom not found"));
        messageRoomMember.setMessageRoom(messageRoom);
        final User createdBy = messageRoomMemberDTO.getUsername() == null ? null : userRepository.findById(messageRoomMemberDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        messageRoomMember.setUser(createdBy);
        messageRoomMember.setIsAdmin(messageRoomMemberDTO.getIsAdmin());
        messageRoomMember.setLastSeen(messageRoomMemberDTO.getLastSeen());
        return messageRoomMember;
    }
}
