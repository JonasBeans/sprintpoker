package be.jonasboon.sprintpoker.player.service;

import be.jonasboon.sprintpoker.player.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerService {

    private final Map<String, Player> activePlayers = new ConcurrentHashMap<>();

    public List<Player> playerJoined(Player player, String sessionId) {
        activePlayers.put(sessionId, player);
        return activePlayers.values().stream().toList();
    }

}
