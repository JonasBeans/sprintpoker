package be.jonasboon.sprintpoker.player;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class PlayerController {

    @MessageMapping("/player.playerJoined")
    @SendTo("/topic/public")
    public Player playerJoined(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(
                headerAccessor.getSessionAttributes()).put("username", player.getUsername()
        );
        return player;
    }
}
