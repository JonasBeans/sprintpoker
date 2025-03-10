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

    private enum CONNECTION_TYPE {
        DISCONNECTED,
        CONNECTED,
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
        handleConnection(disconnectEvent, CONNECTION_TYPE.DISCONNECTED);
    }

    @EventListener
    public void handleWebSocketConnectListenerS(SessionConnectedEvent connectedEvent) {
        handleConnection(connectedEvent, CONNECTION_TYPE.CONNECTED);
    }

    private void handleConnection(AbstractSubProtocolEvent event, CONNECTION_TYPE connectionType) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if ( username != null ) {
            log.info("{} {}", username, connectionType);
            Player player = Player.builder()
                    .username(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", player);
        }
    }
}
