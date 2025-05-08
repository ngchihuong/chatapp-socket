package backend_socket.backend_socket.messageroommember;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageRoomMemberDto {
    private UUID messageRoomId;
    private String username;
    private Boolean isAdmin;
    private LocalDateTime lastSeen;
    private LocalDateTime lastLogin;
}
