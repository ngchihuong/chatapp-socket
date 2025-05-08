package backend_socket.backend_socket.messageroom;

import backend_socket.backend_socket.messagecontent.MessageContentDto;
import backend_socket.backend_socket.user.User;
import backend_socket.backend_socket.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageRoomMapper {

    private final UserRepository userRepository;


    public MessageRoomDto toDTO(final MessageRoom messageRoom, final MessageRoomDto messageRoomDTO) {
        messageRoomDTO.setId(messageRoom.getId());
        messageRoomDTO.setName(messageRoom.getName());
        messageRoomDTO.setIsGroup(messageRoom.getIsGroup());
        messageRoomDTO.setCreatedDate(messageRoom.getCreatedDate());
        messageRoomDTO.setCreatedById(messageRoom.getCreatedBy().getUsername());
        return messageRoomDTO;
    }


    public MessageRoom toEntity(final MessageRoomDto messageRoomDTO, final MessageRoom messageRoom) {
        messageRoom.setId(messageRoomDTO.getId());
        messageRoom.setName(messageRoomDTO.getName());
        messageRoom.setIsGroup(messageRoomDTO.getIsGroup());
        messageRoom.setCreatedDate(messageRoomDTO.getCreatedDate());
        final User createdBy = messageRoomDTO.getCreatedById() == null ? null : userRepository.findById(messageRoomDTO.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        messageRoom.setCreatedBy(createdBy);
        return messageRoom;
    }
}
