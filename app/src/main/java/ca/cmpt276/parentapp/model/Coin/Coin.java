package ca.cmpt276.parentapp.model.Coin;

import androidx.annotation.NonNull;

public class Coin {
    private final String FlipDate;
    private final String PlayersName;
    private final int PlayerChoice; // 0 = head, 1 = tail
    private final boolean WinOrNot;

    public Coin(String flipDate, String playersName, int playerChoice, boolean winOrNot) {
        FlipDate = flipDate;
        PlayersName = playersName;
        PlayerChoice = playerChoice;
        WinOrNot = winOrNot;
    }

    public String getFlipDate() { return FlipDate; }

    public String getPlayersName() { return PlayersName; }

    public int getPlayerChoice() { return PlayerChoice; }

    public boolean isWinOrNot() { return WinOrNot; }

    @NonNull
    @Override
    public String toString() {
        String childChoice = PlayerChoice == 0 ? "head" : "tail";
        return "Flip Date" + FlipDate + "\n" +
                "Child's Name: " + PlayersName + "\n" +
                "Child's Choice: " + childChoice + "\n";
    }
}
