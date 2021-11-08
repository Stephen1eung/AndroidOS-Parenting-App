package ca.cmpt276.parentapp.model;

import java.time.LocalDateTime;

import ca.cmpt276.parentapp.UI.FlipCoinActivity;

public class CoinHistory {
    private LocalDateTime time;
    private String NameOfPlayer;
    private int PlayerChoice; // 0 = head, 1 = tail
    private boolean WinOrLose;

    public CoinHistory(LocalDateTime time, String nameOfPlayer, int playerChoice, boolean winOrLose) {
        this.time = time;
        NameOfPlayer = nameOfPlayer;
        PlayerChoice = playerChoice;
        WinOrLose = winOrLose;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getPlayerChoice() {
        return PlayerChoice;
    }

    public void setPlayerChoice(int playerChoice) {
        PlayerChoice = playerChoice;
    }

    public String getName() {
        return NameOfPlayer;
    }

    public void setName(String name) {
        this.NameOfPlayer = name;
    }

    public boolean getWinOrLose() {
        return WinOrLose;
    }

    public void setWinOrLose(boolean winOrLose) {
        WinOrLose = winOrLose;
    }
}
