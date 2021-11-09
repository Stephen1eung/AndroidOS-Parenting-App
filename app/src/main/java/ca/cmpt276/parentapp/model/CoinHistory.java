package ca.cmpt276.parentapp.model;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class CoinHistory {
    private final String FlipDate;
    private final String PlayersName;
    private final int PlayerChoice; // 0 = head, 1 = tail
    private final boolean WinOrNot;

    public CoinHistory(String flipDate, String playersName, int playerChoice, boolean winOrNot) {
        FlipDate = flipDate;
        PlayersName = playersName;
        PlayerChoice = playerChoice;
        WinOrNot = winOrNot;
    }

    public String getFlipDate() {
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

    @NonNull
    @Override
    public String toString() {
        return "CoinHistory{" +
                "FlipDate=" + FlipDate.toString() +
                ", PlayersName='" + PlayersName + '\'' +
                ", PlayerChoice=" + PlayerChoice +
                ", WinOrNot=" + WinOrNot +
                '}';
    }
}
