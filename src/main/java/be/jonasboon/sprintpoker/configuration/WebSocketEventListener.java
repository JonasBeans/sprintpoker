package be.jonasboon.sprintpoker.configuration;

import be.jonasboon.sprintpoker.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if ( username != null ) {
            log.info("{} disconnected", username);
            Player player = Player.builder()
                    .username(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", String.format("%s disconnected", username));
        } else {
            log.info("Unknown disconnected");
            Player player = Player.builder()
                    .username("Unknown")
                    .build();
            messagingTemplate.convertAndSend("/topic/public", player);
        }
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent connectedEvent) {
        log.info("User connected");
    }

}
