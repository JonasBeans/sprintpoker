package be.jonasboon.sprintpoker.player.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Player {
    private String username;
}
