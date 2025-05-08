package backend_socket.backend_socket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDto> login(@RequestBody final UserDto userDto) {
        return ResponseEntity.ok(userService.login(userDto));
    }

    @MessageMapping("/user/connect")  //receives messages from client sending to(nhận các mes do client gửi tới) /app/user/connect
    @SendTo("/topic/active") //(server)send the response to all clients subscribes to(gửi res tới tất cả client đang sub path này) /topic/active
    public UserDto connect(@RequestBody UserDto userDto) {
        return userService.connect(userDto);
    }

    @MessageMapping("/user/disconnect")  //receives messages from client sending to(nhận các mes do client gửi tới) /app/user/connect
    @SendTo("/topic/active") //(server)send the response to all clients subscribes to(gửi res tới tất cả client đang sub path này) /topic/active
    public UserDto disconnect(@RequestBody UserDto userDto) {
        return userService.logout(userDto.getUsername());
    }

    @GetMapping("/online")
    public ResponseEntity<List<UserDto>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getOnlineUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserDto>> searchUsersByUsername(@PathVariable final String username) {
        return ResponseEntity.ok(userService.searchUsersByUsername(username));
    }


}
