package backend_socket.backend_socket.messageroommember;

import backend_socket.backend_socket.messageroom.MessageRoom;
import backend_socket.backend_socket.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_room_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MessageRoomMemberKey.class)
@Builder
public class MessageRoomMember {
    @Id
    @ManyToOne
    @JoinColumn(name = "message_room_id")
    private MessageRoom messageRoom;

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    private Boolean isAdmin;

    private LocalDateTime lastSeen;
}
