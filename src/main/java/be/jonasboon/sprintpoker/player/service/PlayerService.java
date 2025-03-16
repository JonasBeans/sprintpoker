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
    private final PlayersStatusManager playersStatusManager;

    public PlayerService(PlayersStatusManager playersStatusManager) {
        this.playersStatusManager = playersStatusManager;
    }

    public List<Player> playerJoined(Player player, String sessionId) {
        log.info("{} joined", player);
        playersStatusManager.addPlayer(player);
        activePlayers.put(sessionId, player);
        return activePlayers.values().stream().toList();
    }

    public List<Player> playerLeft(String username, String sessionId) {
        log.info("{} left", username);
        playersStatusManager.removePlayer(activePlayers.get(sessionId));
        activePlayers.remove(sessionId);
        return activePlayers.values().stream().toList();
    }

    public List<Player> playerMadeEstimation(PlayerEstimation estimation, String username, String sessionId) {
        log.info("{} made estimation: {}", username, estimation);
        Player player = activePlayers.get(sessionId);
        player.setEstimation(estimation.getEstimation());
        player.setStatus(Player.PlayerStatus.MADE_ESTIMATION);
        playersStatusManager.movePlayerReady(player);
        return activePlayers.values().stream().toList();
    }

    public List<Player> resetEstimation(String username, String sessionId) {
        log.info("{} reset estimation", username);
        Player player = activePlayers.get(sessionId);
        player.setEstimation(0);
        player.setStatus(Player.PlayerStatus.IS_ESTIMATING);
        playersStatusManager.movePlayerUnready(player);
        return activePlayers.values().stream().toList();
    }

}
