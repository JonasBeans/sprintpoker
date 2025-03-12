package be.jonasboon.sprintpoker.player.controller;

import be.jonasboon.sprintpoker.player.model.Player;
import be.jonasboon.sprintpoker.player.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    @MessageMapping("/player.playerJoined")
    @SendTo("/topic/players.Updates")
    public List<Player> playerJoined(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(
                headerAccessor.getSessionAttributes()).put("username", player.getUsername()
        );
        return playerService.playerJoined(player, headerAccessor.getSessionId());
    }
}
