package be.jonasboon.sprintpoker.configuration;

import be.jonasboon.sprintpoker.player.model.Player;
import be.jonasboon.sprintpoker.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final PlayerService playerService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

        if ( Objects.isNull(username) )  throw new RuntimeException("Unknown player tried to disconnect");
        if ( event.getSessionId().isEmpty() )  throw new RuntimeException("Unknown player tried to disconnect");

        messagingTemplate.convertAndSend("/topic/players.Updates", playerService.playerLeft(username, event.getSessionId()));

        log.info("{} disconnected successfully", username);
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent connectedEvent) {
        log.info("User connected");
    }

}
