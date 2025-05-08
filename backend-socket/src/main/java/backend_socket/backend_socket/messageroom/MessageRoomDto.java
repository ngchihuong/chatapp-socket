package backend_socket.backend_socket.messageroom;

import backend_socket.backend_socket.messagecontent.MessageContentDto;
import backend_socket.backend_socket.messageroommember.MessageRoomMemberDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class MessageRoomDto {
    private UUID id;
    private String name;
    private Boolean isGroup;
    private LocalDateTime createdDate;
    private String createdById;

    private MessageContentDto lastMessage;
    private List<MessageRoomMemberDto> members;
}
