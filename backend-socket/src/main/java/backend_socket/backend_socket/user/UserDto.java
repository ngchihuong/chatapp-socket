package backend_socket.backend_socket.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String username;
    private String password;
    private UserStatus status;
    private LocalDateTime lastLogin = LocalDateTime.now();
    private String avatarUrl;
}
