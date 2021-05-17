import java.util.ArrayList;

public class TurnManager {
    private ArrayList<Player> players;
    private Player currentPlayer;

    public TurnManager(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayer( String name) {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        this.currentPlayer = players.get(currentPlayerIndex +1);

    }

    public void changeTurn() {
        setCurrentPlayer(currentPlayer.getName());
    }
}
