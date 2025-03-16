package be.jonasboon.sprintpoker.player.service;

import be.jonasboon.sprintpoker.player.model.Player;
import be.jonasboon.sprintpoker.player.model.PlayerEstimation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PlayerService {

    private final Map<String, Player> activePlayers = new ConcurrentHashMap<>();

    public List<Player> playerJoined(Player player, String sessionId) {
        log.info("{} joined", player);
        activePlayers.put(sessionId, player);
        return activePlayers.values().stream().toList();
    }

    public List<Player> playerLeft(String username, String sessionId) {
        log.info("{} left", username);
        activePlayers.remove(sessionId);
        return activePlayers.values().stream().toList();
    }

    public List<Player> playerMadeEstimation(PlayerEstimation estimation, String username, String sessionId) {
        log.info("{} made estimation: {}", username, estimation);
        Player player = activePlayers.get(sessionId);
        player.setEstimation(estimation.getEstimation());
        player.setStatus(Player.PlayerStatus.MADE_ESTIMATION);
        return activePlayers.values().stream().toList();
    }
}
