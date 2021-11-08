package ca.cmpt276.parentapp.model;

import java.time.LocalDateTime;

public class CoinHistory {
    private LocalDateTime FlipDate;
    private String PlayersName;
    private int PlayerChoice; // 0 = head, 1 = tail
    private boolean WinOrNot;

    public CoinHistory(LocalDateTime flipDate, String playersName, int playerChoice, boolean winOrNot) {
        FlipDate = flipDate;
        PlayersName = playersName;
        PlayerChoice = playerChoice;
        WinOrNot = winOrNot;
    }

    public LocalDateTime getFlipDate() {
        return FlipDate;
    }

    public String getPlayersName() {
        return PlayersName;
    }

    public int getPlayerChoice() {
        return PlayerChoice;
    }

    public boolean isWinOrNot() {
        return WinOrNot;
    }
}
