import java.util.ArrayList;

public class RuleManager {
    public static int calculatePoints(ArrayList<Coin> pocketedCoins, ArrayList<Coin> defunctedCoins) {
        int points = 0;
        int numberOfCoins = pocketedCoins.size();
        if( !pocketedCoins.isEmpty() && !pocketedCoins.contains(Coin.STRIKER))
            if(numberOfCoins==1)
                points += pocketedCoins.get(0).getPoint();
            else if(pocketedCoins.contains(Coin.RED))
                points += Coin.RED.getPoint();
            else
                points +=2;
        else if(pocketedCoins.contains(Coin.STRIKER))
            points += Coin.STRIKER.getPoint();
        if(!defunctedCoins.isEmpty())
            points -=2;
        return points;
    }

    static boolean isFoul(Integer numberOfFouls) {
        return numberOfFouls == 3;
    }

    static boolean isEligibleForLoseFoul(Integer numberOfLoses) {
        return numberOfLoses==3;
    }

    static int calculatePointsWithFouls(int points, boolean foul, boolean eligibleForLoseFoul) {
        if(foul)
            --points;
        if(eligibleForLoseFoul)
            --points;
        return points;
    }

    static Player calculateWinnerBetween(Player firstPlayer, Player secondPlayer) {
        int maxPoints = Math.max(firstPlayer.getPoints(), secondPlayer.getPoints());
        return firstPlayer.getPoints()==maxPoints ? firstPlayer :secondPlayer;
    }

    static boolean isGameDraw(int firstPlayerPoints, int secondPlayerPoints) {
        if(firstPlayerPoints <5  && secondPlayerPoints <5)
            return true;
        return Math.abs(firstPlayerPoints - secondPlayerPoints) < 3;
    }

    public static ArrayList<Coin> getRemovalCoins(ArrayList<Coin> defunctedCoins, ArrayList<Coin> pocketedCoins) {
        ArrayList<Coin> coinsToRemove = new ArrayList<>(defunctedCoins);
        if(pocketedCoins.contains(Coin.RED))
            coinsToRemove.add(Coin.RED);
        else if(pocketedCoins.size() ==1 && !pocketedCoins.contains(Coin.STRIKER))
            coinsToRemove.addAll(pocketedCoins);
        return coinsToRemove;
    }
}
