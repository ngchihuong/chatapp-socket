package backend_socket.backend_socket.messageroom;

import backend_socket.backend_socket.messagecontent.MessageContent;
import backend_socket.backend_socket.messagecontent.MessageContentDto;
import backend_socket.backend_socket.messagecontent.MessageContentService;
import backend_socket.backend_socket.messageroommember.MessageRoomMember;
import backend_socket.backend_socket.messageroommember.MessageRoomMemberDto;
import backend_socket.backend_socket.messageroommember.MessageRoomMemberService;
import backend_socket.backend_socket.user.User;
import backend_socket.backend_socket.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRoomMapper messageRoomMapper;
    private final UserRepository userRepository;
    private final MessageContentService messageContentService;
    private final MessageRoomMemberService messageRoomMemberService;


    public MessageRoomDto findMessageRoomByMembers(List<String> members) {
        return messageRoomRepository.findMessageRoomByMembers(members, members.size())
                .map(m -> messageRoomMapper.toDTO(m, new MessageRoomDto()))
                .orElse(null);
    }

    @Transactional
    public MessageRoomDto create(final List<String> members, String username) {
        final User user = userRepository.findById(username).orElseThrow();

        MessageRoom messageRoom = MessageRoom.builder()
                .isGroup(members.size() > 2)
                .createdBy(user)
                .members(new ArrayList<>())
                .build();

        final List<User> users = userRepository.findAllByUsernameIn(members);

        users.forEach(u -> {
            final MessageRoomMember messageRoomMember = MessageRoomMember.builder()
                    .messageRoom(messageRoom)
                    .user(u)
                    .isAdmin(u.getUsername().equals(username))
                    .lastSeen(LocalDateTime.now())
                    .build();
            messageRoom.getMembers().add(messageRoomMember);
        });

        //temp
        MessageContent messageContent = MessageContent.builder()
                .content("Hi")
                .dateSent(LocalDateTime.now())
                .messageRoom(messageRoom)
                .user(user)
                .build();

        if (messageRoom.getMessageContents() == null) {
            messageRoom.setMessageContents(new ArrayList<>());
        }
//        messageRoom.getMessageContents().add(messageContent);

        MessageRoom saved = messageRoomRepository.save(messageRoom);

        return messageRoomMapper.toDTO(saved, new MessageRoomDto());
    }


    public List<MessageRoomDto> findMessageRoomAtLeastOneContent(String username) {
        return messageRoomRepository.findMessageRoomAtLeastOneContent(username)
                .stream()
                .map(m -> {
                    final MessageRoomDto roomDto = messageRoomMapper.toDTO(m, new MessageRoomDto());
                    final MessageContentDto lastMessage = messageContentService.getLastMessage(roomDto.getId());
                    roomDto.setLastMessage(lastMessage);
                    final List<MessageRoomMemberDto> members = messageRoomMemberService.findMessageRoomId(roomDto.getId());
                    roomDto.setMembers(members);
                    return roomDto;
                })
                .toList();
    }
}
