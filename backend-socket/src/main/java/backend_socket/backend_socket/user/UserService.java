package backend_socket.backend_socket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto login(UserDto userDto) {
        final User user = userRepository.findById(userDto.getUsername())
                .orElseGet(() -> createUser(userDto));

        validatePassword(userDto, user.getPassword());
        return userMapper.toDTO(user, new UserDto());
    }

    private void validatePassword(UserDto userDto, String password) {
        if (!userDto.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password!");
        }
    }

    private User createUser(UserDto userDto) {
        final User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .lastLogin(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public UserDto connect(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getUsername());
        user.ifPresent(u -> {
            u.setStatus(UserStatus.ONLINE);
            userRepository.save(u);
        });
        return user.map(u -> userMapper.toDTO(u, new UserDto())).orElse(null);
    }

    public List<UserDto> getOnlineUsers() {
        return userRepository.findAllByStatus(UserStatus.ONLINE)
                .stream()
                .map(u -> userMapper.toDTO(u, new UserDto()))
                .toList();
    }

    public UserDto logout(final String username) {
        Optional<User> user = userRepository.findById(username);
        user.ifPresent(u -> {
            u.setStatus(UserStatus.OFFLINE);
            u.setLastLogin(LocalDateTime.now());
            userRepository.save(u);
        });
        return user.map(u -> userMapper.toDTO(u, new UserDto())).orElse(null);
    }

    public List<UserDto> searchUsersByUsername(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCase(username)
                .stream()
                .map(u -> userMapper.toDTO(u, new UserDto()))
                .toList();
    }
}
