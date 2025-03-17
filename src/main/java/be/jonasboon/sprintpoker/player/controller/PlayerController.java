package be.jonasboon.sprintpoker.player.controller;

import be.jonasboon.sprintpoker.player.model.Player;
import be.jonasboon.sprintpoker.player.model.PlayerEstimation;
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
    @SendTo("/topic/players.updates")
    public List<Player> playerJoined(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", player.getUsername());
        return playerService.playerJoined(player, headerAccessor.getSessionId());
    }

    @MessageMapping("/player.madeEstimation")
    @SendTo("/topic/players.estimationUpdates")
    public List<Player> playerMadeEstimation(@Payload PlayerEstimation estimation, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        return playerService.playerMadeEstimation(estimation, username, headerAccessor.getSessionId());
    }

    @MessageMapping("/player.resetEstimation")
    @SendTo("/topic/players.estimationUpdates")
    public List<Player> playerResetEstimation(SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        return playerService.resetEstimation(username, headerAccessor.getSessionId());
    }



}
