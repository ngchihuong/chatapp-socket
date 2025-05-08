package backend_socket.backend_socket.messagecontent;

import backend_socket.backend_socket.messageroom.MessageRoom;
import backend_socket.backend_socket.messageroom.MessageRoomRepository;
import backend_socket.backend_socket.user.User;
import backend_socket.backend_socket.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageContentMapper {
    private final MessageRoomRepository messageRoomRepository;
    private final UserRepository userRepository;

    public MessageContentDto toDto(final MessageContent messageContent, final MessageContentDto messageContentDto) {
        messageContentDto.setId(messageContent.getId());
        messageContentDto.setContent(messageContent.getContent());
        messageContentDto.setDateSent(messageContent.getDateSent());
        messageContentDto.setMessageType(messageContent.getMessageType());
        messageContentDto.setMessageRoomId(messageContent.getMessageRoom().getId());
        messageContentDto.setSender(messageContent.getUser().getUsername());
        return messageContentDto;
    }

    public MessageContent toEntity(MessageContentDto messageContentDto,final MessageContent messageContent  ) {
        messageContent.setId(messageContentDto.getId());
        messageContent.setContent(messageContentDto.getContent());
        messageContent.setDateSent(messageContentDto.getDateSent());
        messageContent.setMessageType(messageContent.getMessageType());

        final MessageRoom messageRoom = messageContentDto.getMessageRoomId() == null
                ? null
                : messageRoomRepository.findById(messageContentDto.getMessageRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Message Room not found!"));
        messageContent.setMessageRoom(messageRoom);

        final User user = messageContentDto.getSender() == null
                ? null
                : userRepository.findById(messageContentDto.getSender())
                        .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        messageContent.setUser(user);

        return messageContent;
    }
}
