import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final List<Coin> currentCoins;

    public Board() {
        this.currentCoins = initializeBoard();
    }

    public void removeCoins(ArrayList<Coin> coins) {
        for (Coin coin : coins) {
            this.currentCoins.remove(coin);
        }
    }

    public ArrayList<Coin> getCurrentCoins(){
        return new ArrayList<>(currentCoins);
    }

    private List<Coin> initializeBoard() {
        List<Coin> coins = new ArrayList<>(Collections.nCopies(9, Coin.BLACK));
        coins.add(Coin.RED);
        coins.add(Coin.STRIKER);
        return coins;
    }

    boolean areCoinsExhausted() {
        return currentCoins.size() == 1 && currentCoins.contains(Coin.STRIKER);
    }
}
