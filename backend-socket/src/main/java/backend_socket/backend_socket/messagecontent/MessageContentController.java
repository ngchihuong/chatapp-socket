package backend_socket.backend_socket.messagecontent;

import backend_socket.backend_socket.messageroommember.MessageRoomMemberDto;
import backend_socket.backend_socket.messageroommember.MessageRoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.prefix}/messagecontents")
public class MessageContentController {
    private final MessageContentService messageContentService;
    private final MessageRoomMemberService messageRoomMemberService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/{roomId}")
    public ResponseEntity<List<MessageContentDto>> getMessagesRoomById(@PathVariable UUID roomId) {
        return ResponseEntity.ok(messageContentService.getMessagesByRoomId(roomId));
    }

    @MessageMapping("/send-message")
    public void sendMessage(@RequestBody MessageContentDto messageContentDto) {
        final MessageContentDto saved = messageContentService.save(messageContentDto);
        final List<MessageRoomMemberDto> members = messageRoomMemberService.findMessageRoomId(messageContentDto.getMessageRoomId());
        members.forEach(member -> {
            simpMessagingTemplate.convertAndSendToUser(
                    member.getUsername(),
                    "/queue/messages",
                    saved
            );
        });
    }
}
