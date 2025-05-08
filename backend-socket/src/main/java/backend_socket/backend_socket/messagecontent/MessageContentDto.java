package backend_socket.backend_socket.messagecontent;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageContentDto {
    private UUID id;
    private String content;
    private LocalDateTime dateSent;
    private MessageType messageType;
    private UUID messageRoomId;
    private String sender;
}
