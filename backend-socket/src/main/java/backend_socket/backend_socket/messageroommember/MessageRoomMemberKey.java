package backend_socket.backend_socket.messageroommember;

import backend_socket.backend_socket.messageroom.MessageRoom;
import backend_socket.backend_socket.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRoomMemberKey implements Serializable {
    private User user;
    private MessageRoom messageRoom;
}
