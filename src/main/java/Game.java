import java.util.ArrayList;
import java.util.Optional;

public class Game {
    private ArrayList<Player> players;
    private Board board;
    private TurnManager turnManager;

    public Game(ArrayList<Player> players, Board board) {
        this.players = players;
        this.board = board;
    }

    public void play(Turn turn) {
        Player currentPlayer = turn.getCurrentPlayer();
        ArrayList<Coin> pocketedCoins = turn.getPocketedCoins();
        ArrayList<Coin> defunctedCoins = turn.getDefunctedCoins();
        if(pocketedCoins.isEmpty())
            currentPlayer.addLose();
        int points = RuleManager.calculatePoints(pocketedCoins, defunctedCoins);
        if(points< 0)
            currentPlayer.addFoul();
        currentPlayer.addPoints(RuleManager.calculatePointsWithFouls(points, isEligibleForFoul(currentPlayer),
                isLoseStreakExhausted(currentPlayer)));
        ArrayList<Coin> removalCoins = RuleManager.getRemovalCoins(defunctedCoins, pocketedCoins);
        board.removeCoins(removalCoins);
        turnManager.changeTurn();
    }

    public void start() {
        turnManager = new TurnManager(players);
    }


    public boolean isOver(){
        return board.areCoinsExhausted();
    }

    public Optional<Player> winner(){
        if(!isOver() || isDraw())
            return Optional.empty();
        return Optional.ofNullable(RuleManager.calculateWinnerBetween(players.get(0), players.get(1)));
    }

    public boolean isDraw(){
        if(!isOver()) return false;
        return RuleManager.isGameDraw(players.get(0).getPoints(), players.get(1).getPoints());
    }

    private boolean isLoseStreakExhausted(Player player) {
        boolean foul = RuleManager.isEligibleForLoseFoul(player.getNumberOfLoses());
        if (foul)
            player.resetLoseStreak();
        return foul;
    }

    private boolean isEligibleForFoul(Player player) {
        boolean foul = RuleManager.isFoul(player.getNumberOfFouls());
        if (foul)
            player.resetNoOfFouls();
        return foul;
    }
}
