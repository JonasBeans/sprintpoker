package be.jonasboon.sprintpoker.player.service;

import be.jonasboon.sprintpoker.player.model.Player;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayersStatusManager {

    /**@Left Players who made an estimation;
     * @Right Total amount of players
     */
    private final MutablePair<List<Player>, List<Player>> readyPlayerCount = MutablePair.of(new ArrayList<>(), new ArrayList<>());

    private final SimpMessagingTemplate messagingTemplate;

    public PlayersStatusManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public boolean isAllPlayersReady() {
        return readyPlayerCount.getRight().isEmpty();
    }

    protected void addPlayer(Player player) {
        readyPlayerCount.getRight().add(player);
    }

    protected void removePlayer(Player player) {
        readyPlayerCount.getRight().remove(player);
        readyPlayerCount.getLeft().remove(player);
    }

    protected void movePlayerReady(Player player) {
        readyPlayerCount.getLeft().add(player);
        readyPlayerCount.getRight().remove(player);
        playerAllReadyStatus();
    }

    protected void movePlayerUnready(Player player) {
        readyPlayerCount.getLeft().remove(player);
        readyPlayerCount.getRight().add(player);
        playerAllReadyStatus();
    }

    protected void resetPlayers() {
        List<Player> players = readyPlayerCount.getLeft();
        readyPlayerCount.getLeft().clear();
        readyPlayerCount.getRight().addAll(players);
    }

    public void playerAllReadyStatus() {
        messagingTemplate.convertAndSend("/topic/estimation.isRevealable", isAllPlayersReady());
    }

}
