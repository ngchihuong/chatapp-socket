package backend_socket.backend_socket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserDto toDTO(final User user, final UserDto userDTO) {
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setStatus(user.getStatus());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        return userDTO;
    }


    public User toEntity(final UserDto userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setStatus(userDTO.getStatus());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        return user;
    }
}
