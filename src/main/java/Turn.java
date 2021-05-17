import java.util.ArrayList;

public class Turn {
    private final ArrayList<Coin> coins;
    private final ArrayList<Coin> defunctedCoins;
    private Player currentPlayer;

    public Turn(Player player, ArrayList<Coin> pocketedCoins, ArrayList<Coin> defunctedCoins) {
        this.currentPlayer = player;
        this.coins = pocketedCoins;
        this.defunctedCoins = defunctedCoins;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    ArrayList<Coin> getPocketedCoins() {
        return new ArrayList<>(coins);
    }

    ArrayList<Coin> getDefunctedCoins() {
        return new ArrayList<>(defunctedCoins);
    }
}
