package backend_socket.backend_socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //cấu hình liên kết mà người dùng sẽ liên kết tới websocket ở backend
        registry.addEndpoint("/api/ws") //client connect to
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //clients subscribe to /topic/abc (client nào sub message này thì nhận về message đó)
        registry.enableSimpleBroker("/topic", "/user");

        //client gửi tới địa chỉ nào đó
        registry.setApplicationDestinationPrefixes("/app"); //client send to app/xyz
        registry.setUserDestinationPrefix("/user");
    }


}
