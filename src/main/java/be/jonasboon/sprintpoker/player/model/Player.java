package be.jonasboon.sprintpoker.player.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class Player implements Serializable {
    private String username;
    private PlayerStatus status;
    private int estimation;

    public enum PlayerStatus {
        MADE_ESTIMATION,
        IS_ESTIMATING
    }
}
